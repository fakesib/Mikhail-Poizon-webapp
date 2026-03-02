package com.fakesibwork.mail.config;

import com.fakesibwork.common.dto.UserDto;
import com.fakesibwork.mail.handlers.ConfirmMailEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class KafkaConfig {

    @Bean
    public Consumer<Message<UserDto>> mailConfirmConsumer(ConfirmMailEventHandler confirmMailEventHandler) {
        return confirmMailEventHandler::mailConfirm;
    }

}
