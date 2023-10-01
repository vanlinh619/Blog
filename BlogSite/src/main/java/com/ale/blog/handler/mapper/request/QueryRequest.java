package com.ale.blog.handler.mapper.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QueryRequest {
    @NotNull(message = "page not blank")
    private Integer page;
    @NotNull(message = "size not blank")
    private Integer size;
    private String sortBy;
    private String sortType;
}
