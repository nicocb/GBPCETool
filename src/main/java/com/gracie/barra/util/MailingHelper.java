package com.gracie.barra.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailingHelper {
	private static final Logger log = Logger.getLogger(MailingHelper.class.getName());

	public static void accountCreationAlert(String school, String user, String id) {
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("nicolas.de.dreuille@gmail.com", "SCP Admin"));
			Address[] addresses = new Address[1];
			addresses[0] = new InternetAddress("nicolas.de.dreuille@gmail.com", "Nico");
			msg.setReplyTo(addresses);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("sebastien.garnier@graciebarra.com", "Seb"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("nicolas.de.dreuille@gmail.com", "Nico"));
			msg.setSubject("New school creation in SCP");
			msg.setText("School " + school + " has been created by " + user
					+ "\nhttps://pce-tool.appspot.com/admin/schools?highlight=" + id);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			log.severe("Couldn't send mail " + e.getMessage());
		}
	}
}
