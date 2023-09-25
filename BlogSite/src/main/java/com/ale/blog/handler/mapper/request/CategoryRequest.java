package com.ale.blog.handler.mapper.request;

import com.ale.blog.handler.utils.MessageValidate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank
    @Size(min = 1, max = 60)
    private String title;

    @Size(min = 1, max = 160)
    private String metaTitle;

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;

    private String content;

    private Long parentId;

    @NotBlank
    private String author;
}
