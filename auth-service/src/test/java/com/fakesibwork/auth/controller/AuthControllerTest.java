package com.fakesibwork.auth.controller;

import com.fakesibwork.auth.config.TestConfig;
import com.fakesibwork.auth.service.LoginAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
public class AuthControllerTest {

    private static final Logger log = LoggerFactory.getLogger(AuthControllerTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private LoginAttemptService loginAttemptService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        loginAttemptService.reset();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        try (var conn = redisConnectionFactory.getConnection()) {
            conn.flushDb();
            log.info("Redis flushed before test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void brutForceAttackTest() throws Exception {
        mockServer.expect(ExpectedCount.manyTimes(), requestTo("http://database-service/api/user/username"))
                .andRespond(withSuccess("{\"username\":\"username\", \"password\":\"pwd\", \"role\":\"USER\"}", MediaType.APPLICATION_JSON));

        String responseUrl = null;

        for (int i = 0; i < 5; i++) {
            var response = mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("username", "username")
                            .param("password", "wrongPwd"))
                    .andReturn();
            responseUrl = response.getResponse().getRedirectedUrl();
        }
        assertTrue(responseUrl.startsWith("/auth/login?error=Your"));
    }

    @Test
    void wrongPasswordTwiceTest() throws Exception {
        mockServer.expect(ExpectedCount.manyTimes(), requestTo("http://database-service/api/user/username"))
                .andRespond(withSuccess("{\"username\":\"username\", \"password\":\"pwd\", \"role\":\"USER\"}", MediaType.APPLICATION_JSON));

        for (int i = 0; i < 2; i++) {
            var response = mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("username", "username")
                            .param("password", "wrongPwd"))
                    .andReturn();
            log.info(String.valueOf(response.getResponse().getStatus()));
            log.info(response.getResponse().getRedirectedUrl());
        }
        var response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "pwd"))
                .andReturn();
        assertEquals("/profile", String.valueOf(response.getResponse().getRedirectedUrl()));
    }
}
