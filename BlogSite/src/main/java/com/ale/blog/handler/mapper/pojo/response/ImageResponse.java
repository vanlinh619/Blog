package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class ImageResponse {
    private String id;
    private String name;
    private LocalDateTime createDate;
}
