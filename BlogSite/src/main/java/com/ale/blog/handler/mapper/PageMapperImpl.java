package com.ale.blog.handler.mapper;

import com.ale.blog.handler.mapper.pojo.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PageMapperImpl<T, R> implements PageMapper<T, R> {
    @Override
    public PageResponse<R> toPageResponse(Page<T> page, Function<T, R> function) {
        PageResponse<R> pageResponse = new PageResponse<>();
        pageResponse.setPage(page.getNumber());
        pageResponse.setTotalPage(page.getTotalPages());
        pageResponse.setContent(page.getContent().stream().map(function).toList());
        return pageResponse;
    }
}
