package com.fakesibwork.database.controller;

import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.database.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        try {
            var postId = postService.createPost(postDto);
            return ResponseEntity.ok("Post created, id is: " + postId.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> allPosts() {
        try {
            var postList = postService.getAllPosts();
            return ResponseEntity.ok().body(postList);
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
