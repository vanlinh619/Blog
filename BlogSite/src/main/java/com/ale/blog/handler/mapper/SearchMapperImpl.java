package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.response.SearchResponse;
import com.ale.blog.handler.mapper.pojo.response.state.EntityType;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchMapperImpl implements SearchMapper {

    @Override
    public SearchResponse toSearchResponse(@NotNull User user) {
        return SearchResponse.builder()
                .id(user.getUsername())
                .view(user.getFirstName() + " " + user.getLastName())
                .type(EntityType.USER)
                .build();
    }

    @Override
    public SearchResponse toSearchResponse(@NotNull Post post) {
        return SearchResponse.builder()
                .id(post.getSlug())
                .view(post.getTitle())
                .type(EntityType.POST)
                .build();
    }

    @Override
    public List<SearchResponse> usersUoSearchResponses(@NotNull List<User> users) {
        return users.stream().map(this::toSearchResponse).toList();
    }

    @Override
    public List<SearchResponse> postsToSearchResponses(@NotNull List<Post> posts) {
        return posts.stream().map(this::toSearchResponse).toList();
    }
}
