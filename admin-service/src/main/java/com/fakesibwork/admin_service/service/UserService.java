package com.fakesibwork.admin_service.service;

import com.fakesibwork.admin_service.feign.UserFeignClient;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserFeignClient userFeignClient;

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public List<UserDto> getAllUsers() {
        return userFeignClient.getUsers();
    }
}
