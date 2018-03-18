package com.gracie.barra.admin.objects;

import java.util.Date;

public class SchoolEvent {
	private Long id;
	private Date date;
	private String description;
	private Long schoolId;
	private SchoolEventObject object;
	private Long objectId;
	private SchoolEventStatus status = SchoolEventStatus.PENDING;
	private SchoolEventOrigin origin = SchoolEventOrigin.SCHOOL;

	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String DESCRIPTION = "descrition";
	public static final String SCHOOL_ID = "schoolId";
	public static final String OBJECT = "object";
	public static final String OBJECT_ID = "objectId";
	public static final String STATUS = "status";
	public static final String ORIGIN = "origin";

	public static class Builder {
		private Long id;
		private Date date;
		private String description;
		private Long schoolId;
		private SchoolEventObject object;
		private Long objectId;
		private SchoolEventStatus status = SchoolEventStatus.PENDING;
		private SchoolEventOrigin origin = SchoolEventOrigin.SCHOOL;

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

		public Builder object(SchoolEventObject object) {
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

		public Builder origin(SchoolEventOrigin origin) {
			this.origin = origin;
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
		this.origin = builder.origin;
	}

	public Date getDate() {
		return date;
	}

	public String getReadableDate() {
		String result = "now";
		float diff = new Date().getTime() - date.getTime();
		if (diff / 1000.0 < 60) {
			result = (int) Math.floor(diff / 1000) + " s";
		} else if (diff / 1000.0 / 60.0 < 60) {
			result = (int) Math.floor(diff / 1000.0 / 60.0) + " m";
		} else if (diff / 1000.0 / 60.0 / 60.0 < 24) {
			result = (int) Math.floor(diff / 1000.0 / 60.0 / 60.0) + " h";
		} else if (diff / 1000.0 / 60.0 / 60.0 / 24.0 < 30) {
			result = (int) Math.floor(diff / 1000.0 / 60.0 / 60.0 / 24.0) + " d";
		} else if (diff / 1000.0 / 60.0 / 60.0 / 24.0 / 365.0 < 1.0) {
			result = (int) Math.floor(diff / 1000.0 / 60.0 / 60.0 / 24.0 / 30.0) + " m";
		} else {
			result = (int) Math.floor(diff / 1000.0 / 60.0 / 60.0 / 24.0 / 365.0) + " y";
		}
		return result;
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

	public SchoolEventObject getObject() {
		return object;
	}

	public void setObject(SchoolEventObject object) {
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

	public SchoolEventOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(SchoolEventOrigin origin) {
		this.origin = origin;
	}

	public enum SchoolEventStatus {
		PENDING, CLICKED, TREATED
	}

	public enum SchoolEventOrigin {
		SCHOOL, GB;

		public SchoolEventOrigin getTwin() {
			return this == GB ? SchoolEventOrigin.SCHOOL : GB;
		}
	}

	public enum SchoolEventObject {
		SCHOOL("School"), PICTURE("Picture"), COMMENT("Comment"), ADMISSION("School"), DOCUMENT("Document"), FEE("Fee");

		private String description;

		SchoolEventObject(String descrition) {
			this.description = descrition;
		}

		public String getDescription() {
			return description;
		}
	}

	public String getRedirect() {
		String redirect = "";
		if (getOrigin() == SchoolEventOrigin.SCHOOL)
			switch (getObject()) {
			case ADMISSION:
			case SCHOOL:
				redirect = "/admin/schools?highlight=" + getSchoolId();
				break;
			case PICTURE:
				redirect = "/admin/schoolCriteriaAdmin/" + getSchoolId() + "?highlight=" + getObjectId();
				break;
			default:
				redirect = "/admin";

			}
		else {
			switch (getObject()) {
			case ADMISSION:
			case FEE:
				redirect = "/admission";
				break;
			case PICTURE:
				redirect = "/schoolCriteria?highlight=" + getObjectId();
				break;
			case DOCUMENT:
				redirect = "/documents";
				break;
			default:
				redirect = "/school";

			}
		}
		return redirect;
	}

}
