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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.base.actions.AbstractGBServlet;

@SuppressWarnings("serial")
public class SchoolCriteriaAdminServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			String[] tokens = req.getPathInfo().split("/");
			Long schoolId = Long.valueOf(tokens[tokens.length - 1]);
			SchoolCertificationDashboard schoolCertificationDashboard = null;
			try {

				schoolCertificationDashboard = getCertificationDao().getSchoolCertificationDashboard(schoolId);
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			String highlight = req.getParameter("highlight");
			if (highlight != null) {
				req.getSession().getServletContext().setAttribute("highlight", highlight);
			}
			req.getSession().getServletContext().setAttribute("schoolCertificationDashboard", schoolCertificationDashboard);
			req.setAttribute("page", "schoolCriteria");
			req.setAttribute("schoolId", schoolId);
			req.setAttribute("mode", "admin");
			req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Boolean revoke = req.getRequestURI().contains("Refuse");

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			String id = req.getParameter("id");
			String comment = req.getParameter("comment");
			String schoolId = req.getParameter("schoolId");

			getCertificationDao().updateSchoolCertificationCriterion(Long.valueOf(id), Long.valueOf(schoolId), null, comment,
					revoke ? null : false);

		} else {
			throw new ServletException("Should be logged to save school");
		}
		resp.sendRedirect("/admin/schoolCriteriaAdmin/" + req.getParameter("schoolId"));
	}
}