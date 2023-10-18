package com.ale.blog.controller;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.ImageMapper;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.ImageResponse;
import com.ale.blog.handler.mapper.pojo.response.PageResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.ImageService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@RequestMapping("api/authorize/image")
@AllArgsConstructor
public class ApiImageController {
    private final ImageService imageService;
    private final PageMapper<Image, ImageResponse> pageMapper;
    private final ImageMapper imageMapper;

    @PostMapping
    public DataResponse uploadFile(Authentication authentication, @RequestParam MultipartFile image) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Image newImage = imageService.saveImage(image, userAccess.getUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .message(newImage.getId().toString())
                .data(imageMapper.toImageResponse(newImage))
                .build();
    }

    @GetMapping()
    public DataResponse getAllImage(Authentication authentication, @Valid PageRequest pageRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Page<Image> page = imageService.getAllByAuthor(userAccess.getUser(), QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Image.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build()
        );
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(page, imageMapper::toImageResponse))
                .build();
    }

    @DeleteMapping("{id}")
    public DataResponse deleteImage(Authentication authentication, @PathVariable String id) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        imageService.deleteImageById(id, userAccess.getUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .message(id)
                .build();
    }
}