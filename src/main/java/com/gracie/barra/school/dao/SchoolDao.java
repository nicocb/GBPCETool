package com.gracie.barra.school.dao;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.gracie.barra.admin.objects.Result;
import com.gracie.barra.school.objects.School;

public interface SchoolDao {
	public School getSchoolByUser(String userId);

	public Long createSchool(School userId);

	public void updateSchool(School userId) throws EntityNotFoundException;

	public Result<School> listSchools(String startCursor);

	void updateSchoolStatus(Long id, Boolean pending) throws EntityNotFoundException;

	public void deleteSchool(Long valueOf);
}
