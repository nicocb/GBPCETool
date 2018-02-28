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
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventOrigin;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.school.objects.School.Belt;
import com.gracie.barra.school.objects.School.SchoolStatus;

@SuppressWarnings("serial")
public class AdmissionServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		School school = null;
		if (isUserLoggedIn(req)) {
			school = getSchoolDao().getSchoolByUser(getCurrentUserId(req));
			if (school != null) {
				req.getSession().getServletContext().setAttribute("school", school);
				req.setAttribute("page", "admission");
			} else {
				throw new ServletException("School not validated");
			}
		} else {
			req.setAttribute("page", "pleaselogin");
		}
		injectSchoolStatus(req, school);

		req.setAttribute("beltList", Belt.values());
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isUserLoggedIn(req)) {
			School school = getSchoolDao().getSchoolByUser(getCurrentUserId(req));
			if (school != null) {
				try {
					getSchoolDao().updateSchoolAgreementStatus(school.getId(), SchoolStatus.VALIDATED);
					SchoolEvent se = new SchoolEvent.Builder()
							.description("School '" + school.getSchoolName() + "' validated its admission form")
							.object(SchoolEventObject.ADMISSION).objectId(school.getId()).schoolId(school.getId())
							.status(SchoolEventStatus.PENDING).origin(SchoolEventOrigin.SCHOOL).build();
					this.getSchoolEventDao().createSchoolEvent(se);

				} catch (EntityNotFoundException e) {
					throw new ServletException("School not found");
				}
			} else {
				throw new ServletException("School not validated");
			}
		} else {
			req.setAttribute("page", "pleaselogin");
		}
		resp.sendRedirect("/admission"); // read what we just wrote
	}

}
