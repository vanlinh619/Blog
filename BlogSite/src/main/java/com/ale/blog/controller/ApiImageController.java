package com.ale.blog.controller;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.service.ImageService;
import com.ale.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@RequestMapping("api/authorize/image")
@AllArgsConstructor
public class ApiImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping
    public DataResponse uploadFile(@RequestParam MultipartFile image, @RequestParam String uuid) {
        Image newImage = imageService.saveImage(image, uuid);
        return DataResponse.builder()
                .status(DataResponse.ResponseStatus.CREATED)
                .name(newImage.getId().toString())
                .build();
    }

    @GetMapping("{uuid}")
    public Page<Image> getAllImage(@PathVariable String uuid, @Valid PageRequest pageRequest) {
        User author = userService.getById(UUID.fromString(uuid));
        return imageService.getAllByAuthor(author, QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Image.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
    }
}
