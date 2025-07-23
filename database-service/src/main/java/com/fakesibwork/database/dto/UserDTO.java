package com.fakesibwork.database.dto;

import com.fakesibwork.database.model.Role;
import lombok.Builder;

@Builder
public class UserDTO {
    public String username;
    public String password;
    public Role role;
}
