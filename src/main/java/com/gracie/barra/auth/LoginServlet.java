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
// [START example]
package com.gracie.barra.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			// Save the relevant profile info and store it in the session.
			User user = userService.getCurrentUser();
			req.getSession().setAttribute("userEmail", user.getEmail());
			req.getSession().setAttribute("userId", user.getUserId());

			String destination = req.getParameter("loginDestination");
			if (destination == null) {
				destination = "school";
			}

			resp.sendRedirect("/" + destination);
		} else {
			String destination = req.getParameter("loginDestination");
			String dest = "/login";
			if (destination != null) {
				dest += "?loginDestination=" + destination;
			}
			resp.sendRedirect(userService.createLoginURL(dest));
		}
	}
}
// [END example]
