package com.ale.blog.handler.mapper.pojo.response;
import com.ale.blog.entity.state.CategoryLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String slug;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CategoryLevel level;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryResponse> children;
}
