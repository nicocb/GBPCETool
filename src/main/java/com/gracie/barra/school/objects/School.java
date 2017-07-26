package com.gracie.barra.school.objects;

public class School {
	private Long id;
	private String name;
	private String userId;
	private String contactMail;
	private String description;
	private Boolean pending;

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String USERID = "userId";
	public static final String CONTACTMAIL = "contactMail";
	public static final String DESCRIPTION = "description";
	public static final String PENDING = "pending";

	public static class Builder {
		private Long id;
		private String name;
		private String userId;
		private String contactMail;
		private String description;
		private Boolean pending;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder contactMail(String contactMail) {
			this.contactMail = contactMail;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder pending(Boolean pending) {
			this.pending = pending;
			return this;
		}

		public School build() {
			return new School(this);
		}
	}

	private School(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.userId = builder.userId;
		this.contactMail = builder.contactMail;
		this.description = builder.description;
		this.pending = builder.pending;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", userId=" + userId + ", contactMail=" + contactMail
				+ ", description=" + description + ", pending=" + pending + "]";
	}

}
