package com.fakesibwork.database.controller;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.database.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(PostDto postDto) {
        try {
            var postId = postService.createPost(postDto);
            return ResponseEntity.ok("Post created, id is: " + postId.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
