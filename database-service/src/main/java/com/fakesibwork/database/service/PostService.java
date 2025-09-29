package com.fakesibwork.database.service;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.database.model.Post;
import com.fakesibwork.database.repository.PostRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Transactional
    public Integer createPost(PostDto postDto) {
        return postRepo.save(Post.builder()
                        .title(postDto.getTitle())
                        .author(postDto.getAuthor())
                        .description(postDto.getDescription())
                        .date(postDto.getDate())
                .build()).getId();
    }
}
