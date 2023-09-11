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
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String title;
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String metaTitle;
    @Pattern(regexp = "^(?:[a-zA-Z0-9]+(?:-[a-zA-Z0-9])*)+$")
    private String slug;
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String content;
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String author;
    private List<Long> tags;
    private List<Long> categories;
}
