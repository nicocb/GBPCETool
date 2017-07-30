package com.gracie.barra.admin.dao;

import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.Result;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;

public interface CertificationDao {

	Long createCertificationCriterion(CertificationCriterion cc);

	CertificationCriterion readCertificationCriterion(Long ccId);

	void updateCertificationCriterion(CertificationCriterion certificationCriterion);

	void deleteCertificationCriterion(Long certificationCriterionId);

	Result<CertificationCriterion> listCertificationCriteria(String startCursorString);

	SchoolCertificationCriterion readSchoolCertificationCriterion(CertificationCriterion cc);

	Result<SchoolCertificationCriterion> listSchoolCertificationCriteria(Long schoolId, String startCursorString);

}
