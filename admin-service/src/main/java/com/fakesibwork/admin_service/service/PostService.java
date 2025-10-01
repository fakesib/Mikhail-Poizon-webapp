package com.fakesibwork.admin_service.service;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.common.exceptions.PostCreatingException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostService {

    private static final String POST_SERVICE_URL = "http://database-service/api/post/";

    private MinioClient minioClient;

    private RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate, MinioClient minioClient) {
        this.restTemplate = restTemplate;
        this.minioClient = minioClient;
    }

    //TODO Extract photo var to variable on front side
    @Transactional
    public void createPost(String author, PostDto postDto, MultipartFile imageFile) throws Exception {

        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("photos").build());
        log.info("Bucket Exists: {}", bucketExists);

        if (bucketExists) {
            String fileName = imageFile.getName() + "_" + UUID.randomUUID();
            uploadFile("photos", fileName, imageFile);
            postDto.setImage_path("photos/" + fileName);
        } else {
            throw new PostCreatingException();
        }

        postDto.setAuthor(author);
        postDto.setDate(LocalDate.now());
        restTemplate.postForEntity(POST_SERVICE_URL + "create", postDto, String.class);
        log.info(postDto.toString());
    }

    private void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }

    public List<PostDto> allPost() {
        return restTemplate.getForEntity(POST_SERVICE_URL + "all", List.class).getBody();
    }
}
