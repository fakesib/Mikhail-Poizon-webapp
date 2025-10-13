package com.fakesibwork.admin_service.feign;

import com.fakesibwork.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "UserFeignClient", url = "database-service", path = "/api/user")
public interface UserFeignClient {

    @GetMapping("/all")
    List<UserDto> getUsers();
}
