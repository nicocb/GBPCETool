package com.gracie.barra.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventOrigin;

public class MailingHelper {
	private static final Logger log = Logger.getLogger(MailingHelper.class.getName());

	private ObjectMapper objectMapper;

	private Queue queue;

	public MailingHelper() {
		queue = QueueFactory.getQueue("mails");
		objectMapper = new ObjectMapper();
	}

	public void notifyEvent(SchoolEventOrigin origin, String school, String contact, String object, String link) {
		if (origin == SchoolEventOrigin.GB && (contact == null || contact.length() == 0)) {
			log.info("No contact for school, won't notify '" + object + "'");
			return;
		}
		Instant now = Instant.now();
		Mail mail = new Mail(now.toEpochMilli(), origin.toString(), school, contact,
				DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneOffset.UTC).format(now) + " " + object, link, false);
		try {
			queue.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(objectMapper.writeValueAsString(mail))
					.tag(mail.getOrigin()));
		} catch (JsonProcessingException e) {
			log.severe("Couldn't push mail " + e.getMessage());
		}
	}

	public void flushAdmin() {
		// Lease only tasks tagged with "process"
		List<TaskHandle> tasks = queue.leaseTasksByTag(500, TimeUnit.MILLISECONDS, 100, "SCHOOL");
		log.info("Admin mails, " + tasks.size() + " tasks");
		if (tasks.size() > 0) {
			Mail merge = tasks.stream().map(task -> deserialize(task)).sorted().collect(Mail.getCollector());
			// dirty but works,
			mailEvent("SCHOOL", null, null, "New certification events : ", merge.getBody());
		}

	}

	private Mail deserialize(TaskHandle task) {
		String payload = new String(task.getPayload(), StandardCharsets.UTF_8);
		try {
			Mail mail = objectMapper.readValue(payload, new TypeReference<Mail>() {
			});
			mail.setBody(mail.getObject() + " (https://pce-tool.appspot.com" + mail.getLink() + ")");
			return mail;
		} catch (IOException e) {
			log.severe("Couldn't pull mail " + payload + "\n" + e.getMessage());
		} finally {
			queue.deleteTask(task);
		}
		return new Mail();
	}

	/**
	 * send mails to schools
	 */
	public void flushSchools() {
		List<TaskHandle> tasks = queue.leaseTasksByTag(500, TimeUnit.MILLISECONDS, 100, "GB");
		log.info("Schools mails, " + tasks.size() + " tasks");
		if (tasks.size() > 0) {

			tasks.stream().map(task -> deserialize(task)).sorted().sorted()
					.collect(Collectors.groupingBy(Mail::getSchool, Mail.getCollector()))
					.forEach((school, mail) -> mailEvent("GB", mail.getSchool(), mail.getContact(), "New certification events : ",
							mail.getBody()));
		}

	}

	public void mailEvent(String origin, String school, String contact, String object, String body) {
		String destMail = origin.equals("GB") ? contact : "support.europe@graciebarra.com";
		String dest = origin.equals("GB") ? school : "Support Europe";
		Message msg = null;
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("noreply@pce-tool.appspotmail.com", "SCP Admin"));
			Address[] addresses = new Address[1];
			addresses[0] = new InternetAddress("support.europe@graciebarra.com", "Support Europe");
			msg.setReplyTo(addresses);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destMail, dest));
			msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("nicolas.de.dreuille@gmail.com", "Nico"));
			msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("sebastien.garnier@graciebarra.com", "Seb"));
			msg.setSubject("[SCP] " + object);
			msg.setText(object + " \n\n" + body);
			log.info("Sending " + object + " to " + destMail + "/" + dest + " : \n\n" + body);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			log.severe("Couldn't send mail " + e.getMessage() + " \n" + msg);
		}
	}

}
