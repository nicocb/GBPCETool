package com.gracie.barra.admin.dao;

import java.util.List;

import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEventsByObject;
import com.gracie.barra.admin.objects.SchoolEventsDashboard;

public interface SchoolEventDao {

	Long createSchoolEvent(SchoolEvent cc);

	SchoolEvent readSchoolEvent(Long seId);

	void updateSchoolEvent(SchoolEvent schoolEvent);

	void deleteSchoolEvent(Long schoolEvent);

	List<SchoolEventsByObject> listSchoolEventsByObject();

	SchoolEventsDashboard listSchoolEvents();

}
