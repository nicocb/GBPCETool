package com.gracie.barra.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// you can also make an authenticated request to logout, but here we
		// choose to
		// simply delete the session variables for simplicity

		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		// rebuild session
		req.getSession();

		for (int c = 0; c < req.getCookies().length; c++) {
			if (req.getCookies()[c].getName().equals("fbsr_123435078353209")) {
				req.getCookies()[c].setMaxAge(-1);
				resp.addCookie(req.getCookies()[c]);
				break;
			}
		}

	}
}
