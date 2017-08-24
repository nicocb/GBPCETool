package com.gracie.barra.admin.dao;

import java.util.List;

import com.gracie.barra.admin.objects.CertificationCriteriaByRank;
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationCriteriaByRank;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;

public interface CertificationDao {

	Long createCertificationCriterion(CertificationCriterion cc);

	CertificationCriterion readCertificationCriterion(Long ccId);

	void updateCertificationCriterion(CertificationCriterion certificationCriterion);

	void deleteCertificationCriterion(Long certificationCriterionId);

	List<CertificationCriterion> listCertificationCriteria();

	List<CertificationCriteriaByRank> listCertificationCriteriaByRank();

	List<SchoolCertificationCriterion> listSchoolCertificationCriteria(Long schoolId);

	List<SchoolCertificationCriteriaByRank> listSchoolCertificationCriteriaByRank(Long schoolId);

	SchoolCertificationDashboard getSchoolCertificationDashboard(Long schoolId);

	SchoolCertificationCriterion updateSchoolCertificationCriterion(Long criterionId, Long schoolId, String picture,
			String comment, Boolean pending);

	SchoolCertificationCriterion readSchoolCertificationCriterion(CertificationCriterion cc, Long schoolID);

}
