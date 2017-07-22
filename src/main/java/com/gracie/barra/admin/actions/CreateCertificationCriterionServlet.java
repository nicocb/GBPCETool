package com.gracie.barra.admin.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.admin.dao.CertificationDao;
import com.gracie.barra.admin.objects.CertificationCriterion;


@SuppressWarnings("serial")
public class CreateCertificationCriterionServlet extends HttpServlet {

	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	      IOException {
	    req.setAttribute("action", "Add");          // Part of the Header in form.jsp
	    req.setAttribute("destination", "createCriterion");  // The urlPattern to invoke (this Servlet)
	    req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
	    req.getRequestDispatcher("/base.jsp").forward(req, resp);
	  }

	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	      IOException {
	    CertificationCriterion certificationCriterion = new CertificationCriterion.Builder()
	        .description(req.getParameter("description"))
	        .action(req.getParameter("action"))
	        .comment(req.getParameter("comment"))
	        .rank(Long.valueOf(req.getParameter("rank")))
	        .build();

	    CertificationDao dao = (CertificationDao) this.getServletContext().getAttribute("dao");
	    try {
	      Long id = dao.createCertificationCriterion(certificationCriterion);
	      resp.sendRedirect("list");   // read what we just wrote
	    } catch (Exception e) {
	      throw new ServletException("Error creating certificationCriterion", e);
	    }
	  }
	}

