package com.ale.blog.handler.utils;

import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Convert {
    public static PageRequest pageRequest(QueryRequest queryRequest) {
        PageRequest pageRequest = PageRequest.of(queryRequest.getPage(), queryRequest.getSize());
        Sort.Direction direction;
        if(queryRequest.getSortType() == null || queryRequest.getSortType().equals(SortType.ASC.name())) {
            direction = Sort.Direction.ASC;
        } else if (queryRequest.getSortType().equals(SortType.DESC.name())) {
            direction = Sort.Direction.DESC;
        } else {
            throw new AppException(DataResponse.builder()
                    .status(Status.FAILED)
                    .code(MessageCode.BAD_QUERY)
                    .build());
        }

        if(queryRequest.getSortBy() != null && !queryRequest.getSortBy().isEmpty()){
            pageRequest = pageRequest.withSort(direction, queryRequest.getSortBy().toArray(new String[0]));
        } else {
            pageRequest = pageRequest.withSort(direction, "id");
        }
        return pageRequest;
    }
}
