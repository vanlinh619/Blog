package com.ale.blog.handler.mapper.request;

import com.ale.blog.handler.utils.MessageValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String metaTitle;
    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;
    @NotBlank
    private String content;
    @NotBlank
    private String author;
    private List<Long> tags;
    private List<Long> categories;
}
