package com.fakesibwork.auth;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerInit
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String REDIS_IMAGE = "redis:7.2-alpine";
    private static final int REDIS_PORT = 6379;

    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
                    .withExposedPorts(REDIS_PORT);

    static {
        REDIS_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String host = REDIS_CONTAINER.getHost();
        Integer port = REDIS_CONTAINER.getMappedPort(REDIS_PORT);

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.redis.host=" + host,
                "spring.redis.port=" + port
        );
    }
}
