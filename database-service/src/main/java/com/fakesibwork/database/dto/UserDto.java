package com.fakesibwork.database.dto;

import com.fakesibwork.database.model.Role;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    public UserDto(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private String username;
    private String password;
    private String email;
    private Role role;
}
