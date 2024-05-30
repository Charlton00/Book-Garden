package com.example.initializer.registration;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SendEmails {
    
    public void sendRegistrationVerification(String userEmail, String userFirstName, String code) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");

        String sender = "team3obs2023@gmail.com";
        String recipient = userEmail;
        String password = "zbvlzcqrwdkmijhn";

        Session session = Session.getInstance(properties, getAuthenticator(sender, password));

        try {
			Transport.send(sendVerificationEmail(session, sender, recipient, userFirstName, code));
			System.out.println("Verification email successfully sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }

	public void sendOrderConfirmation(String userEmail, String userFirstName, String orderDetails, String orderHistoryLink) {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");

		String sender = "team3obs2023@gmail.com";
		String recipient = userEmail;
		String password = "zbvlzcqrwdkmijhn";

		Session session = Session.getInstance(properties, getAuthenticator(sender, password));

		try {
			Transport.send(sendOrderConfirmationMessage(session, sender, recipient, userFirstName, orderDetails, orderHistoryLink));
			System.out.println("Order confirmation sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

    private Authenticator getAuthenticator(String from, String password) {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		return authenticator;
	}

    private MimeMessage sendVerificationEmail(Session session, String sender, String recipient, String userFirstName, String code) {
		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(sender));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			msg.setSubject("BookGarden Registration Verification");
			msg.setText("Hello " + userFirstName + ",\n\n" + "Your verification code is " + code + "."
					+ "\nPlease log in and verify your account with the provided verification code to become an active user.\nThank you!");
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return msg;
	}

	private MimeMessage sendOrderConfirmationMessage(Session session, String sender, String recipient, String userFirstName, String orderDetails, String orderHistoryLink) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("BookGarden Order Confirmation");
			message.setText("Dear " + userFirstName + ",\n\n" +
							"Thank you for your order. Here are your order details:\n\n" +
							orderDetails + "\n\n" +
							"You can view your order history by clicking on the following link:\n" +
							orderHistoryLink);
		} catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}

	public void sendCustomMessage(String userEmail, String userFirstName, String message, String subject) {
		Properties properties = new Properties();

		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");

		String sender = "team3obs2023@gmail.com";
        String recipient = userEmail;
        String password = "zbvlzcqrwdkmijhn";

		Session session = Session.getInstance(properties, getAuthenticator(sender, password));

		try {
			Transport.send(createCustomMessage(session, sender, recipient, subject, userFirstName, message));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private MimeMessage createCustomMessage(Session session, String sender, String recipient, String subject, String userFirstName, String message) {
		MimeMessage msg = new MimeMessage(session);
		String greeting = "<h3>Hello " + userFirstName + ",</h3>";
		String body = "<p>" + message + "<p>";

		try {
			msg.setFrom(new InternetAddress(sender));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			msg.setSubject(subject);
			msg.setContent(greeting + body, "text/html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

}
