package com.gracie.barra.school.objects;

import java.util.Date;

public class School {
	private Long id;
	private String userId;

	private String contactName;
	private String contactMail;
	private String contactPhone;

	private String schoolName;
	private String schoolAddress;
	private String schoolCity;
	private String schoolZip;
	private String schoolCountry;
	private String schoolMail;
	private String schoolPhone;
	private String schoolWeb;

	private String instructorName;
	private Belt instructorBelt;
	private String instructorProfessor;

	private String certificateURL;

	private Date dateCreated;
	private Date dateValidated;

	private SchoolStatus status;
	private SchoolStatus agreementStatus = SchoolStatus.NOT_PROVIDED;
	private SchoolStatus initialFeeStatus = SchoolStatus.NOT_PROVIDED;
	private SchoolStatus monthlyFeeStatus = SchoolStatus.NOT_PROVIDED;

	public static final String ID = "id";
	public static final String USERID = "userId";
	public static final String CONTACT_NAME = "contactName";
	public static final String CONTACT_MAIL = "contactMail";
	public static final String CONTACT_PHONE = "contactPhone";

	public static final String SCHOOL_NAME = "schoolName";
	public static final String SCHOOL_ADDRESS = "schoolAddress";
	public static final String SCHOOL_CITY = "schoolCity";
	public static final String SCHOOL_ZIP = "schoolZip";
	public static final String SCHOOL_COUNTRY = "schoolCountry";
	public static final String SCHOOL_MAIL = "schoolMail";
	public static final String SCHOOL_PHONE = "schoolPhone";
	public static final String SCHOOL_WEB = "schoolWeb";

	public static final String INSTRUCTOR_NAME = "instructorName";
	public static final String INSTRUCTOR_BELT = "instructorBelt";
	public static final String INSTRUCTOR_PROFESSOR = "instructorProfessor";

	public static final String CERTIFICATE_URL = "certificateURL";

	public static final String STATUS = "status";
	public static final String AGREEMENT_STATUS = "agreementStatus";
	public static final String INITIAL_FEE_STATUS = "initialFeeStatus";
	public static final String MONTHLY_FEE_STATUS = "monthlyFeeStatus";
	public static final String DATE_CREATED = "dateCreated";
	public static final String DATE_VALIDATED = "dateValidated";

	public static class Builder {
		private Long id;
		private String userId;
		private String contactName;
		private String contactMail;
		private String contactPhone;
		private String schoolName;
		private String schoolAddress;
		private String schoolCity;
		private String schoolZip;
		private String schoolCountry;
		private String schoolMail;
		private String schoolPhone;
		private String schoolWeb;
		private String instructorName;
		private Belt instructorBelt;
		private String instructorProfessor;
		private String certificateURL;
		private Date dateCreated;
		private Date dateValidated;
		private SchoolStatus status;
		private SchoolStatus agreementStatus;
		private SchoolStatus initialFeeStatus;
		private SchoolStatus monthlyFeeStatus;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder contactName(String contactName) {
			this.contactName = contactName;
			return this;
		}

		public Builder contactMail(String contactMail) {
			this.contactMail = contactMail;
			return this;
		}

		public Builder contactPhone(String contactPhone) {
			this.contactPhone = contactPhone;
			return this;
		}

		public Builder schoolName(String schoolName) {
			this.schoolName = schoolName;
			return this;
		}

		public Builder schoolAddress(String schoolAddress) {
			this.schoolAddress = schoolAddress;
			return this;
		}

		public Builder schoolCity(String schoolCity) {
			this.schoolCity = schoolCity;
			return this;
		}

		public Builder schoolZip(String schoolZip) {
			this.schoolZip = schoolZip;
			return this;
		}

		public Builder schoolCountry(String schoolCountry) {
			this.schoolCountry = schoolCountry;
			return this;
		}

		public Builder schoolMail(String schoolMail) {
			this.schoolMail = schoolMail;
			return this;
		}

		public Builder schoolPhone(String schoolPhone) {
			this.schoolPhone = schoolPhone;
			return this;
		}

		public Builder schoolWeb(String schoolWeb) {
			this.schoolWeb = schoolWeb;
			return this;
		}

		public Builder instructorName(String instructorName) {
			this.instructorName = instructorName;
			return this;
		}

		public Builder instructorBelt(Belt instructorBelt) {
			this.instructorBelt = instructorBelt;
			return this;
		}

		public Builder instructorProfessor(String instructorProfessor) {
			this.instructorProfessor = instructorProfessor;
			return this;
		}

		public Builder certificateURL(String certificateURL) {
			this.certificateURL = certificateURL;
			return this;
		}

		public Builder dateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public Builder dateValidated(Date dateValidated) {
			this.dateValidated = dateValidated;
			return this;
		}

		public Builder status(SchoolStatus status) {
			this.status = status;
			return this;
		}

		public Builder agreementStatus(SchoolStatus agreementStatus) {
			this.agreementStatus = agreementStatus;
			return this;
		}

		public Builder initialFeeStatus(SchoolStatus initialFeeStatus) {
			this.initialFeeStatus = initialFeeStatus;
			return this;
		}

		public Builder monthlyFeeStatus(SchoolStatus monthlyFeeStatus) {
			this.monthlyFeeStatus = monthlyFeeStatus;
			return this;
		}

		public School build() {
			return new School(this);
		}
	}

	private School(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.contactName = builder.contactName;
		this.contactMail = builder.contactMail;
		this.contactPhone = builder.contactPhone;
		this.schoolName = builder.schoolName;
		this.schoolAddress = builder.schoolAddress;
		this.schoolCity = builder.schoolCity;
		this.schoolZip = builder.schoolZip;
		this.schoolCountry = builder.schoolCountry;
		this.schoolMail = builder.schoolMail;
		this.schoolPhone = builder.schoolPhone;
		this.schoolWeb = builder.schoolWeb;
		this.instructorName = builder.instructorName;
		this.instructorBelt = builder.instructorBelt;
		this.instructorProfessor = builder.instructorProfessor;
		this.certificateURL = builder.certificateURL;
		this.dateCreated = builder.dateCreated;
		this.dateValidated = builder.dateValidated;
		this.status = builder.status;
		this.agreementStatus = builder.agreementStatus == null ? SchoolStatus.NOT_PROVIDED : builder.agreementStatus;
		this.initialFeeStatus = builder.initialFeeStatus == null ? SchoolStatus.NOT_PROVIDED : builder.initialFeeStatus;
		this.monthlyFeeStatus = builder.monthlyFeeStatus == null ? SchoolStatus.NOT_PROVIDED : builder.monthlyFeeStatus;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getSchoolMail() {
		return schoolMail;
	}

	public void setSchoolMail(String schoolMail) {
		this.schoolMail = schoolMail;
	}

	public String getSchoolPhone() {
		return schoolPhone;
	}

	public void setSchoolPhone(String schoolPhone) {
		this.schoolPhone = schoolPhone;
	}

	public String getSchoolWeb() {
		return schoolWeb;
	}

	public void setSchoolWeb(String schoolWeb) {
		this.schoolWeb = schoolWeb;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public Belt getInstructorBelt() {
		return instructorBelt;
	}

	public void setInstructorBelt(Belt instructorBelt) {
		this.instructorBelt = instructorBelt;
	}

	public String getInstructorProfessor() {
		return instructorProfessor;
	}

	public void setInstructorProfessor(String instructorProfessor) {
		this.instructorProfessor = instructorProfessor;
	}

	public String getCertificateURL() {
		return certificateURL;
	}

	public void setCertificateURL(String certificateURL) {
		this.certificateURL = certificateURL;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateValidated() {
		return dateValidated;
	}

	public void setDateValidated(Date dateValidated) {
		this.dateValidated = dateValidated;
	}

	public SchoolStatus getStatus() {
		return status;
	}

	public void setStatus(SchoolStatus status) {
		this.status = status;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

	public String getSchoolZip() {
		return schoolZip;
	}

	public void setSchoolZip(String schoolZip) {
		this.schoolZip = schoolZip;
	}

	public String getSchoolCountry() {
		return schoolCountry;
	}

	public void setSchoolCountry(String schoolCountry) {
		this.schoolCountry = schoolCountry;
	}

	public SchoolStatus getAgreementStatus() {
		return agreementStatus;
	}

	public void setAgreementStatus(SchoolStatus agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	public SchoolStatus getInitialFeeStatus() {
		return initialFeeStatus;
	}

	public void setInitialFeeStatus(SchoolStatus initialFeeStatus) {
		this.initialFeeStatus = initialFeeStatus;
	}

	public SchoolStatus getMonthlyFeeStatus() {
		return monthlyFeeStatus;
	}

	public void setMonthlyFeeStatus(SchoolStatus monthlyFeeStatus) {
		this.monthlyFeeStatus = monthlyFeeStatus;
	}

	public enum SchoolStatus {
		NOT_PROVIDED("Not provided", "Default"), PENDING("Pending", "Warning"), VALIDATED("Validated",
				"Success"), NOT_VALIDATED("Not validated", "Danger");

		private String description;
		private String style;

		private SchoolStatus(String d, String s) {
			this.description = d;
			this.style = s;
		}

		public String getDescription() {
			return description;
		}

		public String getStyle() {
			return style;
		}

	}

	public enum Belt {
		WHITE("White"), BLUE("Blue"), PURPLE("Purple"), BROWN("Brown"), BLACK("Black");

		private String description;

		private Belt(String d) {
			this.description = d;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
}
