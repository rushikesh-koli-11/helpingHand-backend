package com.helpingHands.demo.serviceImplTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.helpingHands.demo.services.serviceImpl.EmailService;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    private String toEmail;
    private String subject;
    private String body;

    @BeforeEach
    void setUp() {
        toEmail = "test@example.com";
        subject = "Test Subject";
        body = "This is a test email.";
    }

    @Test
    void testSendEmailWithoutAttachment() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("helpinghandsngo26@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        emailService.sendEmail(toEmail, subject, body);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendEmailWithAttachment() throws Exception {
        byte[] attachment = "Sample attachment content".getBytes();
        String filename = "receipt.pdf";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(toEmail, subject, body, attachment, filename);

        verify(javaMailSender, times(1)).send(eq(mimeMessage));
    }

    @Test
    void testSendEmailWithNullAttachment() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(toEmail, subject, body, null, null);

        verify(javaMailSender, times(1)).send(eq(mimeMessage));
    }
}
