package com.fakesibwork.admin_service.service;

import com.fakesibwork.admin_service.config.RestTemplateConfig;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final String USER_SERVICE_URL = "http://database-service/api/user/";

    private RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = restTemplate.getForEntity(USER_SERVICE_URL + "all", List.class).getBody();
        return users;
    }
}
