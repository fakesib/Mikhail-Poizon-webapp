package com.fakesibwork.profile.service;

import com.fakesibwork.common.dto.UserDto;
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
    private KafkaTemplate<String, UserDto> kafkaTemplate;

    public UserDto getUser(String username) {
        return restTemplate.getForEntity(USER_SERVICE_URL + username, UserDto.class).getBody();
    }

    public void sendConfirmationMail(UserDto userDto) {
        System.out.println(userDto.getUsername() + userDto.getEmail());
        kafkaTemplate.send("confirm-mail-event-topic", userDto.getUsername(), userDto);
    }

    public void updateUser(String username, UserDto userDto) {
        restTemplate.put(USER_SERVICE_URL + username, userDto);
    }
}
