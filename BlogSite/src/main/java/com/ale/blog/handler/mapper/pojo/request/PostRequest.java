package com.ale.blog.handler.mapper.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @Size(max = 2000)
    private String introduction;

    @NotBlank
    private String content;

    @NotNull
    private Long categoryId;

    @NotNull
    private List<Long> tags;
}
