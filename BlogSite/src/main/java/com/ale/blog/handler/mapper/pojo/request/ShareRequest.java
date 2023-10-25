package com.ale.blog.handler.mapper.pojo.request;

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
public class ShareRequest {
    @NotBlank
    private String username;

    @NotNull
    private Long objectId;

    @NotBlank
    private String shareScope;
}
