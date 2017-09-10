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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
		if (highlight != null) {
			req.getSession().getServletContext().setAttribute("highlight", highlight);
		}
		req.getSession().getServletContext().setAttribute("schools", schools);

		req.setAttribute("page", "schools");
		req.setCharacterEncoding("UTF-8");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			String id = req.getParameter("id");
			switch (req.getParameter("action")) {
			case "PUT":
				String status = req.getParameter("pending");
				try {
					getSchoolDao().updateSchoolStatus(Long.valueOf(id),
							Boolean.valueOf(status) ? SchoolStatus.PENDING : SchoolStatus.VALIDATED);
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