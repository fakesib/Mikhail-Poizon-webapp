package com.fakesibwork.admin_service.service;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.common.exceptions.PostCreatingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class PostService {

    private static final String POST_SERVICE_URL = "http://database-service/api/post/";

    private RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void createPost(String author, PostDto postDto, MultipartFile imageFile)
            throws IOException, PostCreatingException {

        //TODO set image file to service

        postDto.setAuthor(author);
        postDto.setDate(LocalDate.now());
        restTemplate.postForEntity(POST_SERVICE_URL + "create", postDto, String.class);
    }
}
