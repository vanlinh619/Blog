package com.ale.blog.handler.mapper.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String type;
    private Boolean seen;
    private String updateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String postSlug;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long relyForCommentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentId;

    private String username;
}
