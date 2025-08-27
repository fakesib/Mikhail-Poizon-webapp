package com.fakesibwork.mail.handlers;

import com.fakesibwork.mail.service.MailService;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmMailEventHandler {

    @Autowired
    private MailService mailService;

    @KafkaListener(topics = "confirm-mail-event-topic")
    public void handler(UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", userDto.getUsername());
        map.put("link", "https://mikhailpoizon.ru/auth/confirm-mail");
        mailService.sendMessageWithAttachment(userDto.getEmail(), "Подтверждение почты",
                "confirm-mail", map);
    }
}
