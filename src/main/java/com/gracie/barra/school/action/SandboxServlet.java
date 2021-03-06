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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.school.objects.School;

@SuppressWarnings("serial")
public class SandboxServlet extends AbstractGBServlet {
	private static final Logger log = Logger.getLogger(SchoolCriteriaRestServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		School school = null;
		if (isUserLoggedIn(req)) {
			injectSchoolStatus(req, school);
		}
		log.info(getServletContext().getInitParameter("facebook.secret"));

		req.setAttribute("page", "sandbox");
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

}
