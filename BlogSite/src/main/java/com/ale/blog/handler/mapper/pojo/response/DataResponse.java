package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
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
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private MessageCode code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;
}
