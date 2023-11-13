package com.ale.blog.handler.mapper.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String username;
    private String content;
    private String createDate;
    private Long childrenSize;
    private String sender;

    //rely on comment
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long supperCommentId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String relyUsername;
}
