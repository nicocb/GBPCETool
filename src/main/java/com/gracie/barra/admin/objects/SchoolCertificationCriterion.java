package com.gracie.barra.admin.objects;

import java.util.List;

public class SchoolCertificationCriterion implements Comparable<SchoolCertificationCriterion> {

	Long id;
	Long schoolId;
	CertificationCriterion criterion;
	SchoolCertificationCriterionStatus status;
	List<CriterionPicture> picture;
	List<CriterionComment> comment;

	public static final String ID = "id";
	public static final String SCHOOL_ID = "school_id";
	public static final String CRITERION_ID = "criterion_id";
	public static final String COMMENT = "comment";
	public static final String PICTURE = "picture";
	public static final String STATUS = "status";

	public static class Builder {
		private Long id;
		private Long schoolId;
		private CertificationCriterion criterion;
		private List<CriterionPicture> picture;
		private SchoolCertificationCriterionStatus status;
		private List<CriterionComment> comment;

		public Builder schoolId(Long schoolId) {
			this.schoolId = schoolId;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder criterion(CertificationCriterion criterion) {
			this.criterion = criterion;
			return this;
		}

		public Builder picture(List<CriterionPicture> picture) {
			this.picture = picture;
			return this;
		}

		public Builder status(SchoolCertificationCriterionStatus status) {
			this.status = status;
			return this;
		}

		public Builder comment(List<CriterionComment> comment) {
			this.comment = comment;
			return this;
		}

		public SchoolCertificationCriterion build() {
			return new SchoolCertificationCriterion(this);
		}
	}

	private SchoolCertificationCriterion(Builder builder) {
		this.criterion = builder.criterion;
		this.picture = builder.picture;
		this.status = builder.status;
		this.comment = builder.comment;
		this.id = builder.id;
		this.schoolId = builder.schoolId;
	}

	public CertificationCriterion getCriterion() {
		return criterion;
	}

	public void setCriterion(CertificationCriterion criterion) {
		this.criterion = criterion;
	}

	public List<CriterionPicture> getPicture() {
		return picture;
	}

	public void setPicture(List<CriterionPicture> picture) {
		this.picture = picture;
	}

	public SchoolCertificationCriterionStatus getStatus() {
		return status;
	}

	public void setStatus(SchoolCertificationCriterionStatus status) {
		this.status = status;
	}

	public List<CriterionComment> getComment() {
		return comment;
	}

	public void setComment(List<CriterionComment> comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public enum SchoolCertificationCriterionStatus {
		NOT_PROVIDED("Not provided", "Default"), PENDING("Pending", "Warning"), VALIDATED("Validated",
				"Success"), NOT_VALIDATED("Not validated", "Danger");

		private String description;
		private String style;

		SchoolCertificationCriterionStatus(String descrition, String style) {
			this.description = descrition;
			this.style = style;
		}

		public String getDescription() {
			return description;
		}

		public String getStyle() {
			return style;
		}

	}

	@Override
	public int compareTo(SchoolCertificationCriterion o) {
		int compare = Long.compare(getCriterion().getScore(), o.getCriterion().getScore());
		if (compare == 0) {
			compare = getCriterion().getDescription().compareTo(o.getCriterion().getDescription());
		}
		return compare;
	}

}
