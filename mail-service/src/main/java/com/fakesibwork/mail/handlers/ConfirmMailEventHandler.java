package com.fakesibwork.mail.handlers;

import com.fakesibwork.mail.service.MailService;
import com.fakesibwork.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class ConfirmMailEventHandler {

    private static final Logger log = LoggerFactory.getLogger(ConfirmMailEventHandler.class);

    private final MailService mailService;

    public ConfirmMailEventHandler(MailService mailService) {
        this.mailService = mailService;
    }

    @Bean
    public Consumer<Message<UserDto>> mailConfirmConsumer() {
        return this::mailConfirm;
    }

    public void mailConfirm(Message<UserDto> message) {
        log.info(String.valueOf(message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION, Integer.class)));
        log.info(String.valueOf(message.getHeaders().get(KafkaHeaders.OFFSET, Long.class)));
        var userDto = message.getPayload();
        Map<String, Object> map = new HashMap<>();
        map.put("username", userDto.getUsername());
        map.put("link", "https://mikhailpoizon.ru/auth/confirm-mail/" + userDto.getVerify_token());
        mailService.sendMessageWithAttachment(userDto.getEmail(), "Подтвердите почту",
                "confirm-mail", map);
    }
}
