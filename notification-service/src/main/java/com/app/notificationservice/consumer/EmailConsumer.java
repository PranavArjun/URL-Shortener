package com.app.notificationservice.consumer;

import com.app.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {
    private final EmailService emailService;

    @KafkaListener(
            topics = "user-registered",
            groupId = "notification-group"
    )
    public void handleUserRegistered(String email){
        System.out.println("******** RECEIVED EVENT ********");
        log.info("Received user registration event for : {}",email);
        emailService.sendWelcomeEmail(email);
    }
}
