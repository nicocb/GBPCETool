package com.gracie.barra.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.google.api.client.util.Strings;
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.base.actions.AbstractGBServlet;
import com.gracie.barra.util.CloudStorageHelper;

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
		rankList.remove(0);
		req.getSession().getServletContext().setAttribute("rankList", rankList);
		req.getSession().getServletContext().setAttribute("criterion", criterion);
		req.setAttribute("destination", "criterion");
		req.setAttribute("page", "criterion");
		req.getRequestDispatcher("/baseAdmin.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		assert ServletFileUpload.isMultipartContent(req);
		CloudStorageHelper storageHelper = getStorageHelper();
		boolean hasFile = false;
		String picture = null;
		String extension = "jpg";
		byte[] pic = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			FileItemIterator iter = new ServletFileUpload().getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (item.isFormField()) {
					params.put(item.getFieldName(), Streams.asString(item.openStream(), "UTF-8"));
				} else if (!Strings.isNullOrEmpty(item.getName())) {
					extension = CloudStorageHelper.checkFileExtension(item.getName());
					pic = IOUtils.toByteArray(item.openStream());
					hasFile = true;
				}
			}
			String score = params.get("score");

			CertificationCriterion certificationCriterion = new CertificationCriterion.Builder()
					.description(params.get("description")).action(params.get("action")).comment(params.get("comment"))
					.rank(CertificationCriterionRank.valueOf(params.get("rank")))
					.score(Long.valueOf(score == null | score.length() == 0 ? "0" : score)).build();
			String id = params.get("id");
			if (!nullOrEmpty(id)) {
				if (hasFile) {
					picture = storageHelper.uploadFile(pic, "pce-tool", "Example-" + id, extension);
					certificationCriterion.setPicture(picture);
				}
				certificationCriterion.setId(Long.valueOf(id));
				getCertificationDao().updateCertificationCriterion(certificationCriterion);
			} else {

				try {
					id = getCertificationDao().createCertificationCriterion(certificationCriterion).toString();
					if (hasFile) {
						picture = storageHelper.uploadFile(pic, "pce-tool", "Example-" + id, extension);
						certificationCriterion.setId(Long.valueOf(id));
						certificationCriterion.setPicture(picture);
						getCertificationDao().updateCertificationCriterion(certificationCriterion);
					}
				} catch (Exception e) {
					throw new ServletException("Error creating certificationCriterion", e);
				}
			}
		} catch (FileUploadException | MetadataException | ImageProcessingException e) {
			throw new ServletException("Couldn't read file");
		}

		resp.sendRedirect("/admin/criteria"); // read what we just wrote
	}

}
