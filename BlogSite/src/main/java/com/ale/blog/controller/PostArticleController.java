package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/post-article")
public class PostArticleController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DataResponse postArticle(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.createPostArticle(postRequest);
        return DataResponse.builder()
                .id(post.getId())
                .status(DataResponse.ResponseStatus.CREATED)
                .build();
    }
}
