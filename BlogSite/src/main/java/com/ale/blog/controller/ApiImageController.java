package com.ale.blog.controller;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.request.ImageUpload;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.handler.utils.MessageCode;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.service.ImageService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@AllArgsConstructor
public class ApiImageController {
    private ImageService imageService;

    @RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
    @PostMapping("api/authorize/image")
    public DataResponse uploadFile(@RequestParam MultipartFile image, @RequestParam String uuid) {
        Image newImage = imageService.saveImage(image, uuid);
        return DataResponse.builder()
                .status(DataResponse.ResponseStatus.CREATED)
                .name(newImage.getId().toString())
                .build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        Resource resource = imageService.getImageResource(id);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

}
