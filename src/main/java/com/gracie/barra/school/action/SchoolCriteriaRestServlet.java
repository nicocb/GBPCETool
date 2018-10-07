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
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.google.api.client.util.Strings;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.gracie.barra.admin.objects.CriterionPicture;
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
@WebServlet(name = "schoolCriteriaRest", value = "/api/schoolCriteria")

public class SchoolCriteriaRestServlet extends AbstractGBServlet {
	private static final Logger log = Logger.getLogger(SchoolCriteriaRestServlet.class.getName());

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
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(new JSONObject(schoolCertificationDashboard));
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = null;
		try {
			School school = null;
			if (isUserLoggedIn(req)) {
				String id;
				String schoolId;
				String comment = null;
				SchoolCertificationCriterion criterion = null;
				boolean hasFile = false;

				try {
					if (!ServletFileUpload.isMultipartContent(req)) {
						switch (req.getParameter("action")) {
						case "DELETE":
							schoolId = req.getParameter("schoolId");
							school = getSchoolDao().getSchool(Long.valueOf(schoolId));
							id = req.getParameter("critId");
							String picId = req.getParameter("picId");
							String idSuffix = "default".equals(picId) ? "" : "-" + picId;
							getStorageHelper().deletePdf("pce-tool", schoolId + "-" + id + idSuffix + ".jpg");
							criterion = getCertificationDao().removePicFromSchoolCertificationCriterion(Long.valueOf(id),
									Long.valueOf(schoolId), picId);
							break;
						case "COMMENT":
							// comments
							id = req.getParameter("id");
							comment = StringEscapeUtils.escapeXml(req.getParameter("comment"));

							schoolId = req.getParameter("schoolId");

							school = getSchoolDao().getSchool(Long.valueOf(schoolId));

							criterion = getCertificationDao().updateSchoolCertificationCriterion(Long.valueOf(id), school, null,
									comment, SchoolCertificationCriterionStatus.NOT_PROVIDED, "School");
							break;
						default:
						}

					} else {
						// picture
						CloudStorageHelper storageHelper = getStorageHelper();
						CriterionPicture picture = null;
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
									extension = CloudStorageHelper.checkFileExtension(item.getName().toLowerCase());
									pic = IOUtils.toByteArray(item.openStream());
									hasFile = true;
								}
							}
						} catch (FileUploadException e) {
							throw new ServletException("Couldn't read file");
						}

						id = params.get("id");
						schoolId = params.get("schoolId");

						try {
							if (hasFile) {
								String uuid = UUID.randomUUID().toString();
								picture = new CriterionPicture(uuid,
										storageHelper.uploadFile(pic, "pce-tool", schoolId + "-" + id + "-" + uuid, extension));
							}
						} catch (MetadataException | ImageProcessingException e1) {
							throw new ServletException("Couldn't read image file");
						}

						school = getSchoolDao().getSchool(Long.valueOf(schoolId));
						criterion = getCertificationDao().updateSchoolCertificationCriterion(Long.valueOf(id), school, picture,
								comment, picture != null ? SchoolCertificationCriterionStatus.PENDING
										: SchoolCertificationCriterionStatus.NOT_PROVIDED,
								"School");

					}
					// serialize response
					result = new JSONObject(criterion).toString(2);
					SchoolEvent se = new SchoolEvent.Builder()
							.description("Criterion '" + criterion.getCriterion().getDescription() + "' for school '"
									+ school.getSchoolName() + (hasFile ? "' updated" : "' commented"))
							.object(SchoolEventObject.PICTURE).objectId(criterion.getId()).schoolId(school.getId())
							.status(SchoolEventStatus.PENDING).build();
					getSchoolEventDao().createSchoolEvent(se);
				} catch (NumberFormatException | EntityNotFoundException e) {
					throw new ServletException("Couldn't find related school");
				}
			} else {
				throw new ServletException("Should be logged to save school");
			}
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(result);
		} catch (Throwable t) {
			JSONObject err = new JSONObject();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			err.put("error", t.getMessage());
			log.warning("Intercepted error " + t.getMessage());
			t.printStackTrace();
			resp.getWriter().print(err);
			resp.setStatus(500);
		}

	}
}
