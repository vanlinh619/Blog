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
public class UserInfoResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String avatarId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String fullName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String story;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String username;
}
