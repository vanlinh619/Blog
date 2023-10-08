package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService extends EntityService<Image, UUID> {
    Image saveImage(MultipartFile image, User author);
    Resource getImageResource(String id);
    Page<Image> getAllByAuthor(User author, QueryRequest queryRequest);
    Boolean deleteImageById(String id, User author);
}
