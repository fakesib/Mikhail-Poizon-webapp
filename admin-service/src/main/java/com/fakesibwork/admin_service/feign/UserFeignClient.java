package com.fakesibwork.admin_service.feign;

import com.fakesibwork.admin_service.config.FeignClientConfig;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "database-service",
        path = "/api/user",
        configuration = FeignClientConfig.class)
public interface UserFeignClient {

    @GetMapping("/all")
    List<UserDto> getUsers();
}
