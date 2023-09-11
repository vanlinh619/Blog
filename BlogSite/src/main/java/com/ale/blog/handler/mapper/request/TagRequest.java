package com.ale.blog.handler.mapper.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String metaTitle;

    @NotBlank
    private String slug;

    private String content;
}
