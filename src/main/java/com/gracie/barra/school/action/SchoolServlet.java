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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.admin.dao.SchoolEventDao;
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.dao.SchoolDao;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.school.objects.School.Belt;

@SuppressWarnings("serial")
public class SchoolServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		School school = null;
		if (userService.isUserLoggedIn()) {
			school = getSchoolDao().getSchoolByUser(userService.getCurrentUser().getUserId());
			if (school == null) {
				req.setAttribute("action", "Create");
				req.setAttribute("destination", "school");
			} else {
				req.setAttribute("action", "Update");
				req.setAttribute("destination", "school");
			}
			req.getSession().getServletContext().setAttribute("school", school);
			req.setAttribute("page", "school");
		} else {
			req.setAttribute("page", "pleaselogin");
		}
		injectSchoolStatus(req, school);
		req.setAttribute("beltList", Belt.values());
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			String id = req.getParameter("id");

			School school = new School.Builder().id(nullOrEmpty(id) ? null : Long.valueOf(id))
					.userId(userService.getCurrentUser().getUserId()).contactMail(req.getParameter(School.CONTACT_MAIL))
					.contactName(req.getParameter(School.CONTACT_NAME)).contactPhone(req.getParameter(School.CONTACT_PHONE))
					.instructorBelt(Belt.valueOf(req.getParameter(School.INSTRUCTOR_BELT)))
					.instructorName(req.getParameter(School.INSTRUCTOR_NAME))
					.instructorProfessor(req.getParameter(School.INSTRUCTOR_PROFESSOR))
					.schoolAddress(req.getParameter(School.SCHOOL_ADDRESS)).schoolMail(req.getParameter(School.SCHOOL_MAIL))
					.schoolName(req.getParameter(School.SCHOOL_NAME)).schoolPhone(req.getParameter(School.SCHOOL_PHONE))
					.schoolWeb(req.getParameter(School.SCHOOL_WEB)).schoolCity(req.getParameter(School.SCHOOL_CITY))
					.schoolZip(req.getParameter(School.SCHOOL_ZIP)).schoolCountry(req.getParameter(School.SCHOOL_COUNTRY))

					.build();

			SchoolDao schoolDao = this.getSchoolDao();
			SchoolEventDao schoolEventDao = this.getSchoolEventDao();
			try {
				if (school.getId() == null) {
					Long schId = schoolDao.createSchool(school);
					SchoolEvent se = new SchoolEvent.Builder()
							.description("School '" + school.getSchoolName() + "' created by "
									+ userService.getCurrentUser().getEmail())
							.object(SchoolEventObject.SCHOOL).objectId(schId).schoolId(schId).status(SchoolEventStatus.PENDING)
							.build();
					schoolEventDao.createSchoolEvent(se);
				} else {
					schoolDao.updateSchool(school);
					SchoolEvent se = new SchoolEvent.Builder().description("School '" + school.getSchoolName() + "' updated")
							.object(SchoolEventObject.SCHOOL).objectId(school.getId()).schoolId(school.getId())
							.status(SchoolEventStatus.PENDING).build();
					schoolEventDao.createSchoolEvent(se);
				}
				resp.sendRedirect("/school"); // read what we just wrote
			} catch (Exception e) {
				throw new ServletException("Error creating certificationCriterion", e);
			}
		} else {
			throw new ServletException("Should be logged to save school");
		}
	}

}
