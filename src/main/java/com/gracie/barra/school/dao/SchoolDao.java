package com.gracie.barra.school.dao;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.school.objects.School.SchoolStatus;
import com.gracie.barra.school.objects.SchoolsByRank;

public interface SchoolDao {
	public School getSchoolByUser(String userId);

	public Long createSchool(School userId);

	public void updateSchool(School userId) throws EntityNotFoundException;

	public List<School> listSchools();

	void updateSchoolStatus(Long id, SchoolStatus pending) throws EntityNotFoundException;

	public void deleteSchool(Long valueOf);

	School getSchool(Long id) throws EntityNotFoundException;

	List<SchoolsByRank> listSchoolsByRank();

	void updateSchoolInitialFeeStatus(Long id, SchoolStatus pending) throws EntityNotFoundException;

	void updateSchoolMonthlyFeeStatus(Long id, SchoolStatus pending) throws EntityNotFoundException;

	public void updateSchoolAgreementStatus(Long id, SchoolStatus validated) throws EntityNotFoundException;

	void addDocumentUrl(Long id, String documentName, String fileName, String url) throws EntityNotFoundException;

	public void removeDocument(Long id, String documentName) throws EntityNotFoundException;
}
