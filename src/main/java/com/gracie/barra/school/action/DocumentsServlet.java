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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.google.api.client.util.Strings;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class DocumentsServlet extends AbstractGBServlet {

	private static final Logger log = Logger.getLogger(DocumentsServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		School school = null;
		if (userService.isUserLoggedIn()) {
			school = getSchoolDao().getSchoolByUser(userService.getCurrentUser().getUserId());
			req.getSession().getServletContext().setAttribute("school", school);
			req.setAttribute("page", "documents");
		} else {
			req.setAttribute("page", "pleaselogin");
		}
		injectSchoolStatus(req, school);
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			treatUpload(req);
			resp.sendRedirect("/admin/schools");
		} else {
			String schoolID = req.getParameter("schoolId");
			String documentName = req.getParameter("documentName");
			String documentFileName = req.getParameter("documentFileName");
			switch (req.getParameter("action")) {

			case "DELETE":
				try {
					getSchoolDao().removeDocument(Long.valueOf(schoolID), documentName);
					getStorageHelper().deletePdf("pce-tool", documentFileName);
				} catch (NumberFormatException | EntityNotFoundException e) {
					throw new ServletException(e);
				} catch (NoSuchMethodError e) {
					log.severe("Could not delete " + documentFileName);
				}

				break;

			default:
				break;
			}

			resp.sendRedirect("/admin/schools");
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
		String docName = params.get("documentName");
		String[] nameElements = name.split("\\.");

		try {
			String fileName = docName + "-" + schoolId + "." + nameElements[nameElements.length - 1];
			String url = getStorageHelper().uploadPdf(file, "pce-tool", fileName);
			getSchoolDao().addDocumentUrl(Long.valueOf(schoolId), docName, fileName, url);
		} catch (Exception e) {
			throw new ServletException("Couldn't read  file", e);
		}
	}
}
