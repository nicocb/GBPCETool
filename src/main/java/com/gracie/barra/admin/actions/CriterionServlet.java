package com.gracie.barra.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.base.actions.AbstractGBServlet;

@SuppressWarnings("serial")
public class CriterionServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		CertificationCriterion criterion = null;
		if (id != null) {
			criterion = getCertificationDao().readCertificationCriterion(Long.valueOf(id));
			req.setAttribute("action", "Update");

		} else {
			req.setAttribute("action", "Add"); // Part of the Header in form.jsp
		}

		List<CertificationCriterionRank> rankList = new ArrayList<>(Arrays.asList(CertificationCriterionRank.values()));
		req.getSession().getServletContext().setAttribute("rankList", rankList);
		req.getSession().getServletContext().setAttribute("criterion", criterion);
		req.setAttribute("destination", "criterion");
		req.setAttribute("page", "criterion");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CertificationCriterion certificationCriterion = new CertificationCriterion.Builder()
				.description(req.getParameter("description")).action(req.getParameter("action"))
				.comment(req.getParameter("comment"))
				.rank(CertificationCriterionRank.fromId(Long.valueOf(req.getParameter("rank"))))
				.score(Long.valueOf(req.getParameter("score"))).build();

		String id = req.getParameter("id");
		if (!nullOrEmpty(id)) {
			certificationCriterion.setId(Long.valueOf(id));
			getCertificationDao().updateCertificationCriterion(certificationCriterion);
		} else {

			try {
				getCertificationDao().createCertificationCriterion(certificationCriterion);
			} catch (Exception e) {
				throw new ServletException("Error creating certificationCriterion", e);
			}
		}
		resp.sendRedirect("/admin/criteria"); // read what we just wrote
	}

}
