package com.ale.blog.handler.utils;

import com.ale.sneakerstoreapi.mapper.QueryRequest;
import com.ale.sneakerstoreapi.util.exception.AppException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class UtilContent {
    public static final String PRODUCT_CODE_FORMAT = "P";
    public static final String ORDER_NUMBER_FORMAT = "OD";
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";


    public static PageRequest pageRequest(QueryRequest queryRequest) {
        PageRequest pageRequest = PageRequest.of(queryRequest.getPage() - 1, queryRequest.getSize());
        Sort.Direction direction;
        if(queryRequest.getSortType() == null || queryRequest.getSortType().equals(ASC)) {
            direction = Sort.Direction.ASC;
        } else if (queryRequest.getSortType().equals(DESC)) {
            direction = Sort.Direction.DESC;
        } else {
            throw new AppException(MessageContent.BAD_QUERY);
        }

        if(queryRequest.getSortBy() != null && !queryRequest.getSortBy().isEmpty()){
            pageRequest = pageRequest.withSort(direction, queryRequest.getSortBy());
        } else {
            pageRequest = pageRequest.withSort(direction, "id");
        }
        return pageRequest;
    }

    public static String generateCode(String prefix, Long  id) {
        return prefix + String.format("%010d", id);
    }
}
