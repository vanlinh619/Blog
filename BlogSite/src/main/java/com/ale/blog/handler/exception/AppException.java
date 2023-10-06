package com.ale.blog.handler.exception;

import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException{
    private DataResponse response;
}
