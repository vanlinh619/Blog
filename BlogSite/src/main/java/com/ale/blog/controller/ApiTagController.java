package com.ale.blog.controller;

import com.ale.blog.entity.Tag;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.request.TagRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
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
public class ApiTagController {
    private final TagService tagService;

    @PostMapping
    public DataResponse createTag(@Valid @RequestBody TagRequest tagRequest) {
        Tag tag = tagService.createTag(tagRequest);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .message(tag.getId().toString())
                .build();
    }
}
