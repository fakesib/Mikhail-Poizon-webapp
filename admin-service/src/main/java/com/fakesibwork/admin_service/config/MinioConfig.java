package com.fakesibwork.admin_service.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("minio.username")
    private String username;

    @Value("minio.password")
    private String password;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://minio-service:9000")   // URL MinIO
                .credentials(username, password) // логин и пароль
                .build();
    }
}
