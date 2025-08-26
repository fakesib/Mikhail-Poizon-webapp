package com.fakesibwork.profile.service.dto;

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
    private String role;
}
