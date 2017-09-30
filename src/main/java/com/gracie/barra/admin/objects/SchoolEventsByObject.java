package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.List;

import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventObject;

public class SchoolEventsByObject {
	private SchoolEventObject object;
	private List<SchoolEvent> events = new ArrayList<>();

	public SchoolEventsByObject(SchoolEventObject object) {
		this.object = object;
	}

	public SchoolEventObject getObject() {
		return object;
	}

	public void setObject(SchoolEventObject object) {
		this.object = object;
	}

	public List<SchoolEvent> getEvents() {
		return events;
	}

	public void setEvents(List<SchoolEvent> events) {
		this.events = events;
	}

}
