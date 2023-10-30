package com.example.application.utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static final String SMTP_HOST = "?";
    private static final String SMTP_PORT = "?";
    private static final String SMTP_USERNAME = "?";
    private static final String SMTP_PASSWORD = "?";

    public static boolean sendEmail(String to, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message, SMTP_USERNAME, SMTP_PASSWORD);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

