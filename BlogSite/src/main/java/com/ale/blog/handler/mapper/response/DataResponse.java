package com.ale.blog.handler.mapper.response;

import com.ale.blog.handler.utils.MessageCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String uuid;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)

    private ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private MessageCode code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    public enum ResponseStatus {
        CREATED,
        FAILED,
        UPDATED,
        DELETED
    }
}
