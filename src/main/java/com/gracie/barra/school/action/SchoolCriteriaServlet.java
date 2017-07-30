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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.admin.objects.Result;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
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
			List<SchoolCertificationCriterion> schoolCertificationCriteria = null;

			try {
				Result<SchoolCertificationCriterion> result = getCertificationDao()
						.listSchoolCertificationCriteria(school.getId(), "0");
				schoolCertificationCriteria = result.result;
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			req.getSession().getServletContext().setAttribute("schoolCertificationCriteria", schoolCertificationCriteria);
			req.setAttribute("page", "schoolCriteria");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		}
	}
}
