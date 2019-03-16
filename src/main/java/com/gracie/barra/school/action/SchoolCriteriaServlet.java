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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

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
				Map<Long, Map<CertificationCriterionRank, Map<Long, SchoolCertificationCriterion>>> schoolCriteria = getCertificationDao()
						.ListSchoolCriteria(school.getId());
				schoolCertificationDashboard = getCertificationDao().getSchoolCertificationDashboard(school.getId(),
						schoolCriteria, getCertificationDao().listCertificationCriteria());
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			injectSchoolStatus(req, school);
			String highlight = req.getParameter("highlight");
			req.getSession().getServletContext().setAttribute("highlight", highlight);
			req.getSession().getServletContext().setAttribute("schoolCertificationDashboard", schoolCertificationDashboard);
			req.setAttribute("schoolId", school.getId());
			req.setAttribute("school", school);
			req.setAttribute("page", "schoolCriteria");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		}
	}

}
