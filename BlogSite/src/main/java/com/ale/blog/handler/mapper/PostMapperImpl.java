package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;

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

    @Override
    public PostResponse toPostResponse(Post post) {
        return mapper.map(post, PostResponse.class);
    }
}
