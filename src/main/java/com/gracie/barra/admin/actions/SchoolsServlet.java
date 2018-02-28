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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventOrigin;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
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
		req.getSession().getServletContext().setAttribute("highlight", highlight);
		req.getSession().getServletContext().setAttribute("schools", schools);

		req.setAttribute("page", "schools");
		req.setCharacterEncoding("UTF-8");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (isUserLoggedIn(req)) {

			String id = req.getParameter("id");
			String status;
			switch (req.getParameter("action")) {
			case "PUT":
				status = req.getParameter("pending");
				try {
					getSchoolDao().updateSchoolStatus(Long.valueOf(id),
							Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					SchoolEvent se = new SchoolEvent.Builder()
							.description("Your school has been validated, you can now proceed to admission form.")
							.object(SchoolEventObject.ADMISSION).objectId(Long.valueOf(id)).schoolId(Long.valueOf(id))
							.status(SchoolEventStatus.PENDING).origin(SchoolEventOrigin.GB).build();
					this.getSchoolEventDao().createSchoolEvent(se);

				} catch (Exception e) {
					throw new ServletException("Error updating school ", e);
				}

				break;
			case "init":
				status = req.getParameter("validated");
				try {
					getSchoolDao().updateSchoolInitialFeeStatus(Long.valueOf(id),
							Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					SchoolEvent se = new SchoolEvent.Builder().description("Your initial fee has been validated.")
							.object(SchoolEventObject.FEE).objectId(Long.valueOf(id)).schoolId(Long.valueOf(id))
							.status(SchoolEventStatus.PENDING).origin(SchoolEventOrigin.GB).build();
					this.getSchoolEventDao().createSchoolEvent(se);
				} catch (Exception e) {
					throw new ServletException("Error updating school ", e);
				}
				break;
			case "monthly":
				status = req.getParameter("validated");
				try {
					getSchoolDao().updateSchoolMonthlyFeeStatus(Long.valueOf(id),
							Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
					SchoolEvent se = new SchoolEvent.Builder().description("Your monthly fee has been validated")
							.object(SchoolEventObject.FEE).objectId(Long.valueOf(id)).schoolId(Long.valueOf(id))
							.status(SchoolEventStatus.PENDING).origin(SchoolEventOrigin.GB).build();
					this.getSchoolEventDao().createSchoolEvent(se);
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

			resp.sendRedirect("/admin/schools");
		} else {
			throw new ServletException("Should be admin to update school");
		}
	}

}