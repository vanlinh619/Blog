package com.ale.blog.handler.mapper.pojo.request;

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
    @Size(min = 1, max = 255)
    private String title;

    private String content;

    private Long parentId;
}
