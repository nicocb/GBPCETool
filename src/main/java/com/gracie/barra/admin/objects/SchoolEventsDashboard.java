package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolEventsDashboard {
	private List<SchoolEvent> events = new ArrayList<>();
	private Map<Long, String> schools = new HashMap<>();

	public List<SchoolEvent> getEvents() {
		return events;
	}

	public void setEvents(List<SchoolEvent> events) {
		this.events = events;
	}

	public Map<Long, String> getSchools() {
		return schools;
	}

	public void setSchools(Map<Long, String> schools) {
		this.schools = schools;
	}

}
