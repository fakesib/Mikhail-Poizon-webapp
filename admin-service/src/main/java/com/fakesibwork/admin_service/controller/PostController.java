package com.fakesibwork.admin_service.controller;

import com.fakesibwork.admin_service.service.PostService;
import com.fakesibwork.common.dto.PostDto;
import com.fakesibwork.common.exceptions.PostCreatingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public String postPage() {
        return "post";
    }

    @GetMapping("/create")
    public String createPostPage(Authentication authentication) {
        return "create-post";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(Authentication authentication, @RequestBody PostDto postDto,
                                     @RequestParam("image") MultipartFile imageFile) {

        try {
            postService.createPost(authentication.getName(), postDto, imageFile);
            return ResponseEntity.ok("Post created");
        } catch (IOException | PostCreatingException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
