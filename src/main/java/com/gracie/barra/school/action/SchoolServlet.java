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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.admin.dao.CertificationDao;
import com.gracie.barra.admin.dao.CertificationDaoDatastoreImpl;
import com.gracie.barra.school.dao.SchoolDao;
import com.gracie.barra.school.dao.SchoolDaoDatastoreImpl;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class SchoolServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SchoolServlet.class.getName());

	@Override
	public void init() throws ServletException {
		CertificationDao dao = new CertificationDaoDatastoreImpl();

		this.getServletContext().setAttribute("dao", dao);
		SchoolDao schoolDao = new SchoolDaoDatastoreImpl();

		this.getServletContext().setAttribute("schoolDao", schoolDao);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.info("Entering servlet.");
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			SchoolDao schoolDao = (SchoolDao) this.getServletContext().getAttribute("schoolDao");
			School school = null;
			try {
				school = schoolDao.getSchoolByUser(userService.getCurrentUser().getUserId());
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			if (school == null) {
				req.setAttribute("action", "Create");
				req.setAttribute("destination", "createSchool");
			} else {
				req.setAttribute("action", "Update");
				req.setAttribute("destination", "updateSchool");
			}
			req.getSession().getServletContext().setAttribute("school", school);
			req.setAttribute("page", "school");
		} else {
			req.setAttribute("page", "welcome");
		}
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			String id = req.getParameter("id");

			School school = new School.Builder().id(nullOrEmpty(id) ? null : Long.valueOf(id))
					.description(req.getParameter("description")).name(req.getParameter("name"))
					.contactMail(req.getParameter("contactMail")).userId(userService.getCurrentUser().getUserId())
					.build();

			SchoolDao schoolDao = (SchoolDao) this.getServletContext().getAttribute("schoolDao");
			try {
				if (school.getId() == null) {
					schoolDao.createSchool(school);
				} else {
					schoolDao.updateSchool(school);
				}
				resp.sendRedirect("/"); // read what we just wrote
			} catch (Exception e) {
				throw new ServletException("Error creating certificationCriterion", e);
			}
		} else {
			throw new ServletException("Should be logged to save school");
		}
	}

	private boolean nullOrEmpty(String id) {
		return id == null || id.length() == 0 || id.equals("null");
	}
}
