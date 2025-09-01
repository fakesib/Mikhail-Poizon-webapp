package com.fakesibwork.database.service;

import com.fakesibwork.common.dto.UserDto;
import com.fakesibwork.common.exceptions.*;
import com.fakesibwork.database.model.User;
import com.fakesibwork.database.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
            throws UserIsPresentException, IncorrectRegistrationPathException {
        if (userRepo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserIsPresentException();
        } else if (!username.equals(userDto.getUsername())) {
            throw new IncorrectRegistrationPathException(username);
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
            throws EmailIsAlreadyConfirmedException, ConfirmMailException, InvalidVerifyTokenException {
        if (verifyToken == null || verifyToken.isEmpty()) {
            throw new InvalidVerifyTokenException();
        }

        try {
            var updated = userRepo.updateVerifyToken(verifyToken);
            if (updated == 0) {
                throw new EmailIsAlreadyConfirmedException();
            }
        } catch (DataAccessException exception) {
            throw new ConfirmMailException("Database error while confirming email" + exception);
        }
    }

    //TODO rewrite method
    public HttpStatus updateUser(String username, UserDto userDto) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(RuntimeException::new);

        if (userRepo.findByEmail(userDto.getEmail()).isEmpty())
            user.setEmail(userDto.getEmail());
        else return HttpStatus.BAD_REQUEST;

        if (userRepo.findByUsername(userDto.getUsername()).isEmpty())
            user.setUsername(userDto.getUsername());
        else return HttpStatus.BAD_REQUEST;

        userRepo.save(user);

        return HttpStatus.ACCEPTED;
    }

}
