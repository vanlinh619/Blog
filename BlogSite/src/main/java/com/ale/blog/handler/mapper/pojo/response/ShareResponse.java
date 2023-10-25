package com.ale.blog.handler.mapper.pojo.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareResponse {
    private Long id;
    private String uuid;
    private Long objectId;
    private String shareScope;
}
