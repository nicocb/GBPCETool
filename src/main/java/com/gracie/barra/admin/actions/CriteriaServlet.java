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

import com.gracie.barra.admin.objects.CertificationCriteriaByRank;
import com.gracie.barra.base.actions.AbstractGBServlet;

@SuppressWarnings("serial")
public class CriteriaServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<CertificationCriteriaByRank> certificationCriteriaByRank = null;
		try {
			certificationCriteriaByRank = getCertificationDao().listCertificationCriteriaByRank();
		} catch (Exception e) {
			throw new ServletException("Error listing certificationCriteria", e);
		}

		req.getSession().getServletContext().setAttribute("certificationCriteriaByRank", certificationCriteriaByRank);
		req.setAttribute("page", "criteria");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getCertificationDao().deleteCertificationCriterion(Long.valueOf(req.getParameter("id")));
		resp.sendRedirect("/admin/criteria");
	}
}
