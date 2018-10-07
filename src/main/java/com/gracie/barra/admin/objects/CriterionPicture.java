package com.gracie.barra.admin.objects;

public class CriterionPicture {
	String id;
	String url;

	public CriterionPicture() {
		super();
	}

	public CriterionPicture(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "CriterionPicture [id=" + id + ", url=" + url + "]";
	}

}
