package com.ale.blog.handler.mapper.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String uuid;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    public enum ResponseStatus {
        CREATED,
        FAILED,
        UPDATED,
        DELETED

    }
}
