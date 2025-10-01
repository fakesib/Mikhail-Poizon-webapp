package com.fakesibwork.database.service;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.database.model.Post;
import com.fakesibwork.database.repository.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                        .image_path(postDto.getImage_path())
                        .date(postDto.getDate())
                .build()).getId();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepo.findAll();

        if (postList.isEmpty())
            throw new RuntimeException("Post list is empty");

        List<PostDto> resultList = new ArrayList<>(postList.size());
        for (Post i : postList) {
            resultList.add(PostDto.builder()
                            .id(i.getId())
                            .author(i.getAuthor())
                            .date(i.getDate())
                            .description(i.getDescription())
                            .title(i.getTitle())
                            .image_path(i.getImage_path())
                    .build());
        }

        return resultList;
    }
}
