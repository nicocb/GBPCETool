package com.gracie.barra.admin.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gracie.barra.admin.objects.CertificationCriteriaByRank;
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.admin.objects.CriterionPicture;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion.SchoolCertificationCriterionStatus;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.school.objects.ScoredSchool;

public interface CertificationDao {

	Long createCertificationCriterion(CertificationCriterion cc);

	CertificationCriterion readCertificationCriterion(Long ccId);

	void updateCertificationCriterion(CertificationCriterion certificationCriterion);

	void deleteCertificationCriterion(Long certificationCriterionId);

	List<CertificationCriterion> listCertificationCriteria();

	List<CertificationCriteriaByRank> listCertificationCriteriaByRank();

	SchoolCertificationCriterion updateSchoolCertificationCriterion(Long criterionId, School school, CriterionPicture picture,
			String comment, SchoolCertificationCriterionStatus status, String author) throws JsonProcessingException, IOException;

	SchoolCertificationCriterion readSchoolCertificationCriterion(CertificationCriterion cc, Long schoolID);

	SchoolCertificationCriterion removePicFromSchoolCertificationCriterion(Long criterionId, Long schoolId, String picId)
			throws JsonProcessingException;

	Map<Long, Map<CertificationCriterionRank, Map<Long, SchoolCertificationCriterion>>> ListSchoolCriteria(Long schoolId);

	ScoredSchool scoreSchool(School school,
			Map<Long, Map<CertificationCriterionRank, Map<Long, SchoolCertificationCriterion>>> schoolCritreria,
			List<CertificationCriterion> criteria);

	SchoolCertificationDashboard getSchoolCertificationDashboard(Long schoolId,
			Map<Long, Map<CertificationCriterionRank, Map<Long, SchoolCertificationCriterion>>> schoolCritreria,
			List<CertificationCriterion> criteriaList);

}
