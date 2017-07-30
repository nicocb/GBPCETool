package com.gracie.barra.admin.objects;

public class SchoolCertificationCriterion {

	Long id;
	Long schoolId;
	CertificationCriterion criterion;
	String picture;
	Boolean pending = true;
	String comment;

	public static final String ID = "id";
	public static final String SCHOOL_ID = "school_id";
	public static final String CRITERION_ID = "criterion_id";
	public static final String COMMENT = "comment";
	public static final String PICTURE = "picture";
	public static final String PENDING = "pending";

	public static class Builder {
		private Long id;
		private Long schoolId;
		private CertificationCriterion criterion;
		private String picture;
		private Boolean pending;
		private String comment;

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

		public Builder picture(String picture) {
			this.picture = picture;
			return this;
		}

		public Builder pending(Boolean pending) {
			this.pending = pending;
			return this;
		}

		public Builder comment(String comment) {
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
		this.pending = builder.pending;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
