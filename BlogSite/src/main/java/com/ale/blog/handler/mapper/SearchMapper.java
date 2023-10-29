package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.response.SearchResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface SearchMapper extends Mapper{
    SearchResponse toSearchResponse(@NotNull User user);
    SearchResponse toSearchResponse(@NotNull Post post);
    List<SearchResponse> usersUoSearchResponses(@NotNull List<User> users);
    List<SearchResponse> postsToSearchResponses(@NotNull List<Post> posts);

}
