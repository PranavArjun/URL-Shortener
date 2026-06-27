package com.app.notificationservice.service.impl;

import com.app.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {
    public final JavaMailSender mailSender;
    @Override
    public void sendWelcomeEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Welcome to URL Shortener!");
        message.setText(
                """
                        Hi,
                        
                        Thank you for registering with URl Shortener.
                        """
        );

        mailSender.send(message);
        log.info("Welcome email sent to {}",email);
    }
}

