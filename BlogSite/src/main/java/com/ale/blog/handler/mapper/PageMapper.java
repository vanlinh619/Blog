package com.ale.blog.handler.mapper;

import com.ale.blog.handler.mapper.pojo.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public interface PageMapper<T, R> extends Mapper{
    PageResponse<R> toPageResponse(Page<T> page, Function<T, R> function);
}
