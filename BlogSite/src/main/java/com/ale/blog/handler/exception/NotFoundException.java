package com.ale.blog.handler.exception;

import com.ale.blog.handler.mapper.response.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private DataResponse response;
}
