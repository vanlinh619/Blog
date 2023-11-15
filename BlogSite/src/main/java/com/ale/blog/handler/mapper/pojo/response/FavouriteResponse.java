package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.entity.state.FavouriteState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponse {
    private Long id;
    private String state;
    private String postSlug;
}
