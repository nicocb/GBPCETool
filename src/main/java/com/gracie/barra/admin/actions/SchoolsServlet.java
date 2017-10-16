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

package com.gracie.barra.admin.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School.SchoolStatus;
import com.gracie.barra.school.objects.SchoolsByRank;

@SuppressWarnings("serial")
public class SchoolsServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<SchoolsByRank> schools = null;
		try {
			schools = getSchoolDao().listSchoolsByRank();
		} catch (Exception e) {
			throw new ServletException("Error listing schools", e);
		}
		String highlight = req.getParameter("highlight");
		if (highlight != null) {
			req.getSession().getServletContext().setAttribute("highlight", highlight);
		}
		req.getSession().getServletContext().setAttribute("schools", schools);

		req.setAttribute("page", "schools");
		req.setCharacterEncoding("UTF-8");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			if (ServletFileUpload.isMultipartContent(req)) {
				treatUpload(req);
			} else {
				String id = req.getParameter("id");
				String status;
				switch (req.getParameter("action")) {
				case "PUT":
					status = req.getParameter("pending");
					try {
						getSchoolDao().updateSchoolStatus(Long.valueOf(id),
								Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					} catch (Exception e) {
						throw new ServletException("Error updating school ", e);
					}

					break;
				case "init":
					status = req.getParameter("validated");
					try {
						getSchoolDao().updateSchoolInitialFeeStatus(Long.valueOf(id),
								Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					} catch (Exception e) {
						throw new ServletException("Error updating school ", e);
					}

					break;
				case "monthly":
					status = req.getParameter("validated");
					try {
						getSchoolDao().updateSchoolMonthlyFeeStatus(Long.valueOf(id),
								Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					} catch (Exception e) {
						throw new ServletException("Error updating school ", e);
					}

					break;
				case "DELETE":
					getSchoolDao().deleteSchool(Long.valueOf(req.getParameter("id")));
					break;

				default:
					break;
				}
			}
			resp.sendRedirect("/admin/schools");
		} else {
			throw new ServletException("Should be admin to update school");
		}
	}

	private void treatUpload(HttpServletRequest req) throws ServletException {
		Map<String, String> params = new HashMap<String, String>();
		byte[] file = null;
		String name = "";

		try {
			FileItemIterator iter = new ServletFileUpload().getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (item.isFormField()) {
					params.put(item.getFieldName(), Streams.asString(item.openStream(), "UTF-8"));
				} else if (!Strings.isNullOrEmpty(item.getName())) {
					file = IOUtils.toByteArray(item.openStream());
					name = item.getName();
				}
			}
		} catch (FileUploadException | IOException e) {
			throw new ServletException("Couldn't read file");
		}

		String schoolId = params.get("schoolId");
		String[] nameElements = name.split("\\.");

		try {
			String url = getStorageHelper().uploadPdf(file, "pce-tool", schoolId, nameElements[nameElements.length - 1]);
			getSchoolDao().updateSchoolCertificateURL(Long.valueOf(schoolId), url);
		} catch (EntityNotFoundException | IOException | MetadataException | ImageProcessingException e) {
			throw new ServletException("Couldn't read image file", e);
		}
	}

}