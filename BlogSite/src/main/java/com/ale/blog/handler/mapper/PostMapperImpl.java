package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.mapper.pojo.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@AllArgsConstructor
public class PostMapperImpl implements PostMapper {
    private final ModelMapper mapper;

    @Override
    public Post toPost(PostRequest postRequest) {
        Post post = mapper.map(postRequest, Post.class);
        post.setState(PostState.CREATED);
        post.setCreateDate(Instant.now());
        return post;
    }
}
