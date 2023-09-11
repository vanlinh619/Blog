package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.response.ResponseData;
import com.ale.blog.handler.utils.MessageType;
import com.ale.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseData postArticle(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.createPostArticle(postRequest);
        return ResponseData.builder()
                .id(post.getId())
                .status(ResponseData.ResponseStatus.CREATED)
                .build();
    }
}
