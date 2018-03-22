package com.gracie.barra.admin.batch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gracie.barra.base.actions.AbstractGBServlet;

@SuppressWarnings("serial")
public class AsyncMailServlet extends AbstractGBServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String[] tokens = req.getPathInfo().split("/");
		String dest = tokens[tokens.length - 1];
		switch (dest) {
		case "gb":
			getMailHelper().flushAdmin();
			break;
		case "schools":
			getMailHelper().flushSchools();
			break;

		default:
			resp.sendError(403);
			break;
		}

	}

}
