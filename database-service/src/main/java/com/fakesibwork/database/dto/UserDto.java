package com.fakesibwork.database.dto;

import com.fakesibwork.database.model.Role;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private String email;
    private Role role;
}
