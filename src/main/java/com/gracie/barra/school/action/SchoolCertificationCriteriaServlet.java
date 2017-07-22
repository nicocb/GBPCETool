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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.admin.dao.CertificationDao;
import com.gracie.barra.admin.dao.DatastoreDao;
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.Result;

@SuppressWarnings("serial")
public class SchoolCertificationCriteriaServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
    CertificationDao dao = new DatastoreDao();
      
    this.getServletContext().setAttribute("dao", dao);
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
	  CertificationDao dao = (CertificationDao) this.getServletContext().getAttribute("dao");
    String startCursor = req.getParameter("cursor");
    List<CertificationCriterion> certificationCriteria = null;
    String endCursor = null;
    try {
      Result<CertificationCriterion> result = dao.listCertificationCriterions(startCursor);
      certificationCriteria = result.result;
      endCursor = result.cursor;
    } catch (Exception e) {
      throw new ServletException("Error listing certificationCriteria", e);
    }
    req.getSession().getServletContext().setAttribute("certificationCriteria", certificationCriteria);
    StringBuilder certificationCriterionNames = new StringBuilder();
    for (CertificationCriterion certificationCriterion : certificationCriteria) {
      certificationCriterionNames.append(certificationCriterion.getDescription() + " ");
    }
    req.setAttribute("cursor", endCursor);
    req.setAttribute("page", "list");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
