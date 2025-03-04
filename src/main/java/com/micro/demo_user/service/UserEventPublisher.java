package com.micro.demo_user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {

    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishUserCreatedEvent(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
