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

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class SchoolCriteriaServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			School school = getSchoolDao().getSchoolByUser(userService.getCurrentUser().getUserId());
			if (school == null) {
				throw new ServletException("Session probably expired");
			}
			SchoolCertificationDashboard schoolCertificationDashboard = null;

			try {
				schoolCertificationDashboard = getCertificationDao().getSchoolCertificationDashboard(school.getId());
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			req.getSession().getServletContext().setAttribute("schoolCertificationDashboard", schoolCertificationDashboard);
			req.setAttribute("schoolStatus", school.getPending() ? "Pending" : "Validated");
			req.setAttribute("schoolId", school.getId());
			req.setAttribute("page", "schoolCriteria");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			String id = req.getParameter("id");
			String picture = req.getParameter("picture");
			String schoolId = req.getParameter("schoolId");

			SchoolCertificationCriterion criterion = getCertificationDao().updateSchoolCertificationCriterion(Long.valueOf(id),
					Long.valueOf(schoolId), picture, null, true);

			School school;
			try {
				school = getSchoolDao().getSchool(Long.valueOf(schoolId));
				SchoolEvent se = new SchoolEvent.Builder()
						.description("Criterion '" + criterion.getCriterion().getDescription() + "' for school '"
								+ school.getName() + "' updated")
						.object("CRITERION").objectId(criterion.getId()).schoolId(school.getId())
						.status(SchoolEventStatus.PENDING).build();
				getSchoolEventDao().createSchoolEvent(se);
			} catch (NumberFormatException | EntityNotFoundException e) {
				throw new ServletException("Couldn't find related school");
			}

		} else {
			throw new ServletException("Should be logged to save school");
		}
		resp.sendRedirect("/schoolCriteria");
	}
}
