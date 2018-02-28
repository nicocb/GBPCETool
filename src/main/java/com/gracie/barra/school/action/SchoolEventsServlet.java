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

import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.admin.objects.SchoolEventsDashboard;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class SchoolEventsServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (isUserLoggedIn(req)) {
			School school = getSchoolDao().getSchoolByUser(getCurrentUserId(req));
			if (school == null) {
				throw new ServletException("Session probably expired");
			}
			SchoolEventsDashboard schoolEvents = null;
			try {
				schoolEvents = getSchoolEventDao().listSchoolEvents(school.getId());
			} catch (Exception e) {
				throw new ServletException("Error listing schoolEvents", e);
			}
			req.getSession().getServletContext().setAttribute("schoolEvents", schoolEvents);

			injectSchoolStatus(req, school);
			req.setAttribute("page", "schoolEvents");
			req.setAttribute("mode", "school");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Boolean delete = req.getRequestURI().contains("Delete");

		String id = req.getParameter("id");

		if (delete) {
			resp.sendRedirect("/events");
			getSchoolEventDao().deleteSchoolEvent(Long.valueOf(id));
		} else {
			SchoolEvent event = getSchoolEventDao().readSchoolEvent(Long.valueOf(id));
			if (event.getStatus() == SchoolEventStatus.PENDING) {
				event.setStatus(SchoolEventStatus.CLICKED);
				getSchoolEventDao().updateSchoolEvent(event);
			}
			resp.sendRedirect(event.getRedirect());

		}

	}

}