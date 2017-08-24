package com.gracie.barra.admin.objects;

import java.util.Date;

public class SchoolEvent {
	private Long id;
	private Date date;
	private String description;
	private Long schoolId;
	private String object;
	private Long objectId;
	private SchoolEventStatus status = SchoolEventStatus.PENDING;

	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String DESCRIPTION = "descrition";
	public static final String SCHOOL_ID = "schoolId";
	public static final String OBJECT = "object";
	public static final String OBJECT_ID = "objectId";
	public static final String STATUS = "status";

	public static class Builder {
		private Long id;
		private Date date;
		private String description;
		private Long schoolId;
		private String object;
		private Long objectId;
		private SchoolEventStatus status;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder date(Date date) {
			this.date = date;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder schoolId(Long schoolId) {
			this.schoolId = schoolId;
			return this;
		}

		public Builder object(String object) {
			this.object = object;
			return this;
		}

		public Builder objectId(Long objectId) {
			this.objectId = objectId;
			return this;
		}

		public Builder status(SchoolEventStatus status) {
			this.status = status;
			return this;
		}

		public SchoolEvent build() {
			return new SchoolEvent(this);
		}
	}

	private SchoolEvent(Builder builder) {
		this.id = builder.id;
		this.date = builder.date;
		this.description = builder.description;
		this.schoolId = builder.schoolId;
		this.object = builder.object;
		this.objectId = builder.objectId;
		this.status = builder.status;
	}

	public Date getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public void setStatus(SchoolEventStatus status) {
		this.status = status;
	}

	public SchoolEventStatus getStatus() {
		return status;
	}

	public enum SchoolEventStatus {
		PENDING, CLICKED, TREATED
	}

}
