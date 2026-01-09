package com.helpingHands.demo.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

/**
 * Service class for handling email operations such as sending simple emails
 * and emails with attachments.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Sends a simple email without attachments.
     * 
     * @param toEmail Recipient email address
     * @param subject Email subject
     * @param body    Email body content
     */
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("helpinghandsngo26@gmail.com"); // Setting sender email
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        
        javaMailSender.send(message); // Sending the email
    }

    /**
     * Sends an email with an attachment.
     * 
     * @param toEmail    Recipient email address
     * @param subject    Email subject
     * @param body       Email body content
     * @param attachment Byte array representing the attachment
     * @param filename   Name of the attachment file
     */
    public void sendEmail(String toEmail, String subject, String body, byte[] attachment, String filename) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Enabling HTML format in email body

            // Adding attachment if available
            if (attachment != null) {
                helper.addAttachment(filename, new ByteArrayResource(attachment));
            }

            javaMailSender.send(message); // Sending the email
            System.out.println("✅ Donation email sent successfully with receipt attachment!");
        } catch (Exception e) {
            System.err.println("❌ Error sending donation email: " + e.getMessage());
        }
    }
}
