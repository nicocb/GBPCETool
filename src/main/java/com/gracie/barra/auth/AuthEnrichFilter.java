
package com.gracie.barra.auth;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gracie.barra.auth.dao.SessionDao;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;

public class AuthEnrichFilter implements Filter {
	private static final Logger log = Logger.getLogger(AuthEnrichFilter.class.getName());

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletReq;

		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserLoggedIn()) {
			User user = userService.getCurrentUser();
			req.getSession().setAttribute("userEmail", user.getEmail());
			req.getSession().setAttribute("userId", user.getUserId());
			req.getSession().setAttribute("loginFrom", "Google");

		} else {
			// User can also login from FB.
			// Try to get accessToken from session first
			SessionDao sessionDao = (SessionDao) req.getSession().getServletContext().getAttribute("sessionDao");
			String accessToken = null;
			// log.info("Session : " + req.getSession().getId());
			accessToken = sessionDao.getToken(req.getSession().getId());
			FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
			try {
				if (accessToken != null && !facebookClient.debugToken(accessToken).isValid()) {
					accessToken = null;
					sessionDao.deleteToken(req.getSession().getId());
				}
			} catch (Throwable t) {
				// Try not to care about #100
				if (t instanceof FacebookOAuthException && ((FacebookOAuthException) t).getErrorCode() == 100) {
					log.info("Don't care about this error " + accessToken + " " + t.getMessage());

				} else {
					log.severe("Issue with token " + accessToken + " " + t.getMessage());
					accessToken = null;
					sessionDao.deleteToken(req.getSession().getId());
				}
			}
			if (accessToken == null) {
				String accessCode = null;
				for (int c = 0; req.getCookies() != null && c < req.getCookies().length && accessCode == null; c++) {
					if (req.getCookies()[c].getName().equals("fbsr_123435078353209")) {
						accessCode = new JSONObject(
								new String(Base64.getDecoder().decode(req.getCookies()[c].getValue().split("\\.")[1])))
										.getString("code");
						// Expire authorization code once used
						HttpServletResponse resp = (HttpServletResponse) servletResp;
						req.getCookies()[c].setMaxAge(0);
						resp.addCookie(req.getCookies()[c]);
						break;
					}
				}
				if (accessCode != null) {
					try {
						facebookClient = new DefaultFacebookClient(Version.LATEST);
						accessToken = facebookClient
								.obtainUserAccessToken("123435078353209",
										servletReq.getServletContext().getInitParameter("facebook.secret"), "", accessCode)
								.getAccessToken();
						sessionDao.storeToken(req.getSession().getId(), accessToken);
						log.info("Token issued : " + accessToken);
					} catch (Throwable t) {
						log.severe("Issue with token " + accessToken + " code " + accessCode + "" + t.getMessage());
						sessionDao.deleteToken(req.getSession().getId());
					}
				}
			}

			if (accessToken != null) {
				facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
				com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class);
				req.getSession().setAttribute("userEmail", user.getName());
				req.getSession().setAttribute("userId", user.getId());
				req.getSession().setAttribute("loginFrom", "Facebook");
				log.info("Token " + accessToken + " belongs to  " + user.getName());

			} else {
				// remove user from session
				req.getSession().removeAttribute("userEmail");
				req.getSession().removeAttribute("userId");
			}
		}

		chain.doFilter(servletReq, servletResp);

	}

	@Override
	public void destroy() {
	}
}
