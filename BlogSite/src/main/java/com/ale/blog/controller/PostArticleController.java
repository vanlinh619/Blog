package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.PostRequest;
import com.ale.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RolesAllowed(UserRole.Fields.ADMIN)
@AllArgsConstructor
@RequestMapping("api/authorize/")
public class PostArticleController {
    private final PostService postService;

    @PostMapping("/post-article")
    @ResponseStatus(HttpStatus.OK)
    public Post postArticle(@Valid @RequestBody PostRequest postRequest) {
        return postService.createPostArticle(postRequest);
    }
}
