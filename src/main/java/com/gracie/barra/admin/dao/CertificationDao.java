package com.gracie.barra.admin.dao;

import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.Result;

public interface CertificationDao {

	Long createCertificationCriterion(CertificationCriterion cc);

	CertificationCriterion readCertificationCriterion(Long ccId);

	void updateCertificationCriterion(CertificationCriterion certificationCriterion);

	void deleteCertificationCriterion(Long certificationCriterionId);

	Result<CertificationCriterion> listCertificationCriterions(String startCursorString);

}
