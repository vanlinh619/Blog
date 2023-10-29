package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.handler.mapper.pojo.response.state.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private String id;
    private String view;
//    private String snippet;
    private EntityType type;
}
