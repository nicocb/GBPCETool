package com.gracie.barra.util;

import java.util.stream.Collector;

public class Mail implements Comparable<Mail> {
	private Long date;
	private String origin;
	private String school;
	private String contact;
	private String object;
	private String link;
	private String body;

	public Mail() {
		super();
	}

	public Mail(Long date, String origin, String school, String contact, String object, String link, boolean creation) {
		this.origin = origin;
		this.school = school;
		this.contact = contact;
		this.object = object;
		this.link = link;
		this.date = date;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Mail merge(Mail mail2) {
		this.body = body == null ? mail2.body : body + "\n" + mail2.body;
		this.contact = mail2.contact;
		this.school = mail2.school;
		return this;
	}

	public static Collector<Mail, Mail, Mail> getCollector() {
		return Collector.of(Mail::new, (mail1, mail2) -> mail1.merge(mail2), (mail1, mail2) -> mail1.merge(mail2));
	}

	@Override
	public int compareTo(Mail o) {
		return date.compareTo(o.date);
	}

}