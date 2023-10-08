package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/post-article")
public class ApiPostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DataResponse postArticle(Authentication authentication, @Valid @RequestBody PostRequest postRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Post post = postService.createPostArticle(postRequest, userAccess.getUser());
        return DataResponse.builder()
                .id(post.getId())
                .status(DataResponse.ResponseStatus.CREATED)
                .build();
    }
}
