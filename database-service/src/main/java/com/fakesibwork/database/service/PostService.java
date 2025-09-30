package com.fakesibwork.database.service;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.database.model.Post;
import com.fakesibwork.database.repository.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PostService {

    private PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Transactional
    public Integer createPost(PostDto postDto) {
        log.info(postDto.toString());
        return postRepo.save(Post.builder()
                        .title(postDto.getTitle())
                        .author(postDto.getAuthor())
                        .description(postDto.getDescription())
                        .date(postDto.getDate())
                .build()).getId();
    }
}
