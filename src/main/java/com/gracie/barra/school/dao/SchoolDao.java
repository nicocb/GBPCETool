package com.gracie.barra.school.dao;

import com.gracie.barra.school.objects.School;

public interface SchoolDao {
	public School getSchoolByUser(String userId);

	public Long createSchool(School userId);

	public void updateSchool(School userId);
}
