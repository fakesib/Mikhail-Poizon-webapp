package com.fakesibwork.database.service;

import com.fakesibwork.common.dto.UserDto;
import com.fakesibwork.common.exceptions.*;
import com.fakesibwork.database.model.User;
import com.fakesibwork.database.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDto getUser(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(RuntimeException::new);
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .verify_token(user.getVerify_token())
                .build();
    }

    public void addUser(String username, UserDto userDto)
            throws UserIsPresentException, IncorrectUsernameException {
        if (userRepo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserIsPresentException();
        } else if (!username.equals(userDto.getUsername())) {
            throw new IncorrectUsernameException(username);
        } else {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());
            user.setVerify_token(UUID.randomUUID().toString());
            userRepo.save(user);
        }
    }

    public void confirmMail(String verifyToken)
            throws EmailIsAlreadyConfirmedException, InvalidVerifyTokenException {

        if (verifyToken == null || verifyToken.isEmpty()) {
            throw new InvalidVerifyTokenException();
        }

        var updated = userRepo.updateVerifyToken(verifyToken);
        if (updated == 0) {
            throw new EmailIsAlreadyConfirmedException();
        }
    }

    public void updateUser(String username, UserDto userDto)
            throws EmailIsPresentException, UsernameIsPresentException, UserNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        boolean emailNotNull = user.getEmail() != null
                && !user.getEmail().isBlank();

        boolean emailChanged = emailNotNull
                && !user.getEmail().equals(userDto.getEmail());

        boolean emailIsPresent = emailNotNull && emailChanged
                && userRepo.findByEmail(userDto.getEmail()).isPresent();

        if (userRepo.findByUsername(userDto.getUsername()).isPresent()
                && !user.getUsername().equals(userDto.getUsername())) {
            throw new UsernameIsPresentException(userDto.getUsername());
        } else if (emailIsPresent) {
            throw new EmailIsPresentException(userDto.getEmail());
        } else if (emailChanged) {
            user.setVerify_token(UUID.randomUUID().toString());
        } else {
            user.setUsername(userDto.getUsername());
            if (emailNotNull)
                user.setEmail(userDto.getEmail());
            userRepo.save(user);
        }
    }
}
