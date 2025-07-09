package com.fakesibwork.profile.dto;

import lombok.Builder;

@Builder
public class UserDTO {
    public String username;
    public String password;
    public String role;
}
