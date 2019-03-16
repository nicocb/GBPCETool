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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion.SchoolCertificationCriterionStatus;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventOrigin;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class SchoolCriteriaAdminServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (isUserLoggedIn(req)) {
			String[] tokens = req.getPathInfo().split("/");
			Long schoolId = Long.valueOf(tokens[tokens.length - 1]);
			SchoolCertificationDashboard schoolCertificationDashboard = null;
			School school = null;
			try {
				Map<Long, Map<CertificationCriterionRank, Map<Long, SchoolCertificationCriterion>>> schoolCriteria = getCertificationDao()
						.ListSchoolCriteria(schoolId);
				schoolCertificationDashboard = getCertificationDao().getSchoolCertificationDashboard(schoolId, schoolCriteria,
						getCertificationDao().listCertificationCriteria());
				school = getSchoolDao().getSchool(schoolId);
			} catch (Exception e) {
				throw new ServletException("Error listing certificationCriteria", e);
			}
			String highlight = req.getParameter("highlight");
			req.getSession().getServletContext().setAttribute("highlight", highlight);

			req.getSession().getServletContext().setAttribute("schoolCertificationDashboard", schoolCertificationDashboard);
			req.setAttribute("page", "schoolCriteria");
			req.setAttribute("schoolId", schoolId);
			req.setAttribute("school", school);
			req.setAttribute("mode", "admin");
			req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			Boolean revoke = req.getRequestURI().contains("Refuse");
			Boolean validate = req.getRequestURI().contains("Validate");
			String result = null;
			if (isUserLoggedIn(req)) {
				String id = req.getParameter("id");
				String comment = StringEscapeUtils.escapeXml(req.getParameter("comment"));
				String schoolId = req.getParameter("schoolId");

				School school = null;
				try {
					school = getSchoolDao().getSchool(Long.valueOf(schoolId));
				} catch (NumberFormatException | EntityNotFoundException e) {
					throw new ServletException("Couldn't find related school");
				}

				SchoolCertificationCriterion criterion = getCertificationDao().updateSchoolCertificationCriterion(
						Long.valueOf(id), school, null, comment, revoke ? SchoolCertificationCriterionStatus.NOT_VALIDATED
								: validate ? SchoolCertificationCriterionStatus.VALIDATED : null,
						"GB");
				result = new JSONObject(criterion).toString(2);

				SchoolEvent se = new SchoolEvent.Builder()
						.description("Criterion '" + criterion.getCriterion().getDescription()
								+ (revoke ? " refused" : (validate ? " validated" : " commented")))
						.object(SchoolEventObject.PICTURE).objectId(criterion.getId()).schoolId(Long.valueOf(schoolId))
						.status(SchoolEventStatus.PENDING).origin(SchoolEventOrigin.GB).build();
				this.getSchoolEventDao().createSchoolEvent(se);

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
			t.printStackTrace();
			resp.getWriter().print(err);
			resp.setStatus(500);
		}
	}
}
