package com.gracie.barra.admin.objects;

import java.util.Date;

public class CriterionComment {
	Date date;
	String author;
	String comment;

	public CriterionComment(Date date, String author, String comment) {
		super();
		this.date = date;
		this.author = author;
		this.comment = comment;
	}

	public CriterionComment() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
