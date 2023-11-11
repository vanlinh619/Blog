package com.ale.blog.handler.mapper.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @NotBlank
    @Size(max = 255)
    private String content;

    @NotBlank
    private String postSlug;

    private Long replyCommentId;
}
