package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.handler.mapper.request.ImageUpload;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface ImageService extends EntityService<Image, UUID> {
    Image saveImage(MultipartFile image, String uuid);
    Resource getImageResource(String id);
}
