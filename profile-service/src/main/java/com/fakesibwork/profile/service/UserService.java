package com.fakesibwork.profile.service;

import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final String USER_SERVICE_URL = "http://database-service:8083/api/user/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public UserDto getUser(String username) {
        return restTemplate.getForEntity(USER_SERVICE_URL + username, UserDto.class).getBody();
    }

    public void sendConfirmationMail(String username) {
        kafkaTemplate.send("confirm-mail-event-topic", username, username);
    }
}
