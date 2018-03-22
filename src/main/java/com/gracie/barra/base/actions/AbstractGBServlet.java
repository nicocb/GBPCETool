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

package com.gracie.barra.base.actions;

import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.gracie.barra.admin.dao.CertificationDao;
import com.gracie.barra.admin.dao.CertificationDaoDatastoreImpl;
import com.gracie.barra.admin.dao.SchoolEventDao;
import com.gracie.barra.admin.dao.SchoolEventDaoDatastoreImpl;
import com.gracie.barra.auth.dao.SessionDao;
import com.gracie.barra.auth.dao.SessionDaoDatastoreImpl;
import com.gracie.barra.school.dao.SchoolDao;
import com.gracie.barra.school.dao.SchoolDaoDatastoreImpl;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.util.CloudStorageHelper;
import com.gracie.barra.util.MailingHelper;

@SuppressWarnings("serial")
public abstract class AbstractGBServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AbstractGBServlet.class.getName());

	@Override
	public void init() throws ServletException {
		log.info("Init " + this.getClass());
		if (this.getServletContext().getAttribute("certificationDao") == null) {
			CertificationDao dao = new CertificationDaoDatastoreImpl();

			this.getServletContext().setAttribute("certificationDao", dao);

			SchoolDao schoolDao = new SchoolDaoDatastoreImpl(dao);

			this.getServletContext().setAttribute("schoolDao", schoolDao);

			MailingHelper mailingHelper = new MailingHelper();

			this.getServletContext().setAttribute("mailingHelper", mailingHelper);

			SchoolEventDao schoolEventDao = new SchoolEventDaoDatastoreImpl(schoolDao, mailingHelper);

			this.getServletContext().setAttribute("schoolEventDao", schoolEventDao);

			CloudStorageHelper storageHelper = new CloudStorageHelper();

			this.getServletContext().setAttribute("storageHelper", storageHelper);

			SessionDao sessionDao = new SessionDaoDatastoreImpl();
			this.getServletContext().setAttribute("sessionDao", sessionDao);
		}

	}

	protected void injectSchoolStatus(HttpServletRequest req, School school) {
		req.setAttribute("schoolStatus",
				school == null || school.getStatus() == null ? "Not provided" : school.getStatus().getDescription());
		req.setAttribute("agreementStatus", school == null || school.getAgreementStatus() == null ? "Not provided"
				: school.getAgreementStatus().getDescription());
	}

	protected CertificationDao getCertificationDao() {
		return (CertificationDao) this.getServletContext().getAttribute("certificationDao");
	}

	protected SchoolDao getSchoolDao() {
		return (SchoolDao) this.getServletContext().getAttribute("schoolDao");
	}

	protected SchoolEventDao getSchoolEventDao() {
		return (SchoolEventDao) this.getServletContext().getAttribute("schoolEventDao");
	}

	protected CloudStorageHelper getStorageHelper() {
		return (CloudStorageHelper) this.getServletContext().getAttribute("storageHelper");
	}

	protected MailingHelper getMailHelper() {
		return (MailingHelper) this.getServletContext().getAttribute("mailingHelper");
	}

	protected boolean nullOrEmpty(String id) {
		return id == null || id.length() == 0 || id.equals("null");
	}

	protected String getCurrentUserId(HttpServletRequest req) {
		return (String) req.getSession().getAttribute("userId");
	}

	protected String getCurrentUserMail(HttpServletRequest req) {
		return (String) req.getSession().getAttribute("userEmail");
	}

	protected boolean isUserLoggedIn(HttpServletRequest req) {
		return req.getSession().getAttribute("userId") != null;
	}
}
