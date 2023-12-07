package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.ImageType;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public interface ImageService extends EntityService<Image, UUID> {
    Image upsertImage(@Nonnull MultipartFile image, @Nonnull User author, @Nonnull ImageType type);
    Resource getImageResource(String id);
    Page<Image> getAllByAuthor(User author, QueryRequest queryRequest);
    Boolean deleteImageById(String id, User author);
    Boolean deleteAvatar(UUID id);
    Optional<Image> getAvatar(@Nullable User user);
}
