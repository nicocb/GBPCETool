/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gracie.barra.school.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.google.api.client.util.Strings;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion.SchoolCertificationCriterionStatus;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.util.CloudStorageHelper;

@SuppressWarnings("serial")
public class SchoolCriteriaServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (isUserLoggedIn(req)) {
			School school = getSchoolDao().getSchoolByUser(getCurrentUserId(req));
			if (school == null) {
				throw new ServletException("Session probably expired");
			}
			SchoolCertificationDashboard schoolCertificationDashboard = null;

			try {
				schoolCertificationDashboard = getCertificationDao().getSchoolCertificationDashboard(school.getId());
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			injectSchoolStatus(req, school);
			req.getSession().getServletContext().setAttribute("highlight", null);
			req.getSession().getServletContext().setAttribute("schoolCertificationDashboard", schoolCertificationDashboard);
			req.setAttribute("schoolId", school.getId());
			req.setAttribute("school", school);
			req.setAttribute("page", "schoolCriteria");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		School school = null;
		if (isUserLoggedIn(req)) {
			assert ServletFileUpload.isMultipartContent(req);
			CloudStorageHelper storageHelper = getStorageHelper();
			boolean hasFile = false;
			String picture = null;
			String extension = "jpg";
			byte[] pic = null;
			Map<String, String> params = new HashMap<String, String>();
			try {
				FileItemIterator iter = new ServletFileUpload().getItemIterator(req);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					if (item.isFormField()) {
						params.put(item.getFieldName(), Streams.asString(item.openStream(), "UTF-8"));
					} else if (!Strings.isNullOrEmpty(item.getName())) {
						extension = CloudStorageHelper.checkFileExtension(item.getName());
						pic = IOUtils.toByteArray(item.openStream());
						hasFile = true;
					}
				}
			} catch (FileUploadException e) {
				throw new ServletException("Couldn't read file");
			}

			String id = params.get("id");
			String schoolId = params.get("schoolId");
			String comment = params.get("comment");

			try {
				if (hasFile) {
					picture = storageHelper.uploadFile(pic, "pce-tool", schoolId + "-" + id, extension);
				}
			} catch (MetadataException | ImageProcessingException e1) {
				throw new ServletException("Couldn't read image file");
			}

			SchoolCertificationCriterion criterion = getCertificationDao().updateSchoolCertificationCriterion(Long.valueOf(id),
					Long.valueOf(schoolId), picture, comment, picture != null ? SchoolCertificationCriterionStatus.PENDING
							: SchoolCertificationCriterionStatus.NOT_PROVIDED);

			try {
				school = getSchoolDao().getSchool(Long.valueOf(schoolId));
				SchoolEvent se = new SchoolEvent.Builder()
						.description("Criterion '" + criterion.getCriterion().getDescription() + "' for school '"
								+ school.getSchoolName() + "' updated")
						.object(hasFile ? SchoolEventObject.PICTURE : SchoolEventObject.COMMENT).objectId(criterion.getId())
						.schoolId(school.getId()).status(SchoolEventStatus.PENDING).build();
				getSchoolEventDao().createSchoolEvent(se);
			} catch (NumberFormatException | EntityNotFoundException e) {
				throw new ServletException("Couldn't find related school");
			}

		} else {
			throw new ServletException("Should be logged to save school");
		}
		injectSchoolStatus(req, school);
		resp.sendRedirect("/schoolCriteria");
	}
}
