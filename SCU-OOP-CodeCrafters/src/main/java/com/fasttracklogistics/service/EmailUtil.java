package com.fasttracklogistics.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtil {

    // 🔐 Replace these with your actual Gmail address and App Password
    private static final String FROM_EMAIL = "your_email@gmail.com";
    private static final String FROM_PASSWORD = "your_app_password"; // Use App Password from Gmail

    public static void sendEmail(String toEmail, String subject, String body) {
        // Set mail properties for Gmail SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");                      // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");           // Enable STARTTLS
        props.put("mail.smtp.host", "smtp.gmail.com");            // Gmail SMTP server
        props.put("mail.smtp.port", "587");                       // TLS port

        // Create session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            // Create and configure email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("✅ Email successfully sent to " + toEmail);

        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email to " + toEmail);
            e.printStackTrace();
        }
    }
}
