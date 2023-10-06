package com.ale.blog.handler.mapper.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {
    @NotNull(message = "page not blank")
    private Integer page;
    @NotNull(message = "size not blank")
    private Integer size;
    private String sortBy;
    private String sortType;
}
