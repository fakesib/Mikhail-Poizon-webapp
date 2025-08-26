package com.fakesibwork.profile.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic confirmMailTopic() {
        return TopicBuilder.name("confirm-mail-event-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
