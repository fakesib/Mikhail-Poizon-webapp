package dto;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String email;
    private String verify_token;
    private Role role;
}
