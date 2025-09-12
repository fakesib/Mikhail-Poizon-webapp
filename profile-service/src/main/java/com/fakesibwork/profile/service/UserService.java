package com.fakesibwork.profile.service;

import com.fakesibwork.common.dto.UserDto;
import com.fakesibwork.common.exceptions.ConfirmMailException;
import com.fakesibwork.common.exceptions.ProfileUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final String USER_SERVICE_URL = "http://database-service/api/user/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, UserDto> kafkaTemplate;

    public UserDto getUser(String username) {
        return restTemplate.getForEntity(USER_SERVICE_URL + username, UserDto.class).getBody();
    }

    public void sendConfirmationMail(UserDto userDto) throws ConfirmMailException {
        if (userDto.getEmail() != null && userDto.getUsername() != null) {
            kafkaTemplate.send("confirm-mail-event-topic", userDto.getUsername(), userDto);
        } else {
            throw new ConfirmMailException("Email or username is null");
        }
    }

    public void updateUser(String username, UserDto userDto) throws ProfileUpdateException {
        try {
            restTemplate.exchange(
                    USER_SERVICE_URL + "update/" + username,
                    HttpMethod.POST,
                    new HttpEntity<>(userDto),
                    String.class);
        } catch (RestClientException exception) {
            throw new ProfileUpdateException(exception.getMessage());
        }
    }
}
