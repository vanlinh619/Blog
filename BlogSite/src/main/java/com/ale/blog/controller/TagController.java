package com.ale.blog.controller;

import com.ale.blog.entity.Tag;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.request.TagRequest;
import com.ale.blog.handler.mapper.response.ResponseData;
import com.ale.blog.service.TagService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/tag")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseData createTag(@Valid @RequestBody TagRequest tagRequest) {
        Tag tag = tagService.createTag(tagRequest);
        return ResponseData.builder()
                .id(tag.getId())
                .status(ResponseData.ResponseStatus.CREATED)
                .build();
    }
}
