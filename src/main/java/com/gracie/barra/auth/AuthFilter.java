/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gracie.barra.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletReq;
		HttpServletResponse resp = (HttpServletResponse) servletResp;

		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn() && userService.isUserAdmin()) {
			req.getSession().setAttribute("userEmail", userService.getCurrentUser().getEmail());
			req.getSession().setAttribute("userId", userService.getCurrentUser().getUserId());
			req.getSession().setAttribute("loginFrom", "Google");
			chain.doFilter(servletReq, servletResp);
		} else {
			req.setAttribute("page", "pleaseadmin");
			req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
		}
	}

	@Override
	public void destroy() {
	}
}
