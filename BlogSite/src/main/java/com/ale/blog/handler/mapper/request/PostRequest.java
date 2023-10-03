package com.ale.blog.handler.mapper.request;

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
    @Size(min = 1, max = 60)
    private String title;

    @NotBlank
    @Size(min = 1, max = 160)
    private String metaTitle;

    @Size(min = 0, max = 1000)
    private String introduction;

    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    @Size(min = 1, max = 100)
    private String slug;

    @NotBlank
    private String content;

    @NotBlank
    private String author;

    @NotNull
    private List<Long> tags;

    @NotNull
    private List<@NotNull Long> categories;
}
