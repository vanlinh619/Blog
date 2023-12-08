package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;
import com.ale.blog.handler.utils.Format;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PostMapperImpl implements PostMapper {
    private final ModelMapper mapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    @Override
    public Post toPost(PostRequest postRequest) {
        Post post = mapper.map(postRequest, Post.class);
        post.setState(PostState.PUBLIC);
        post.setCreateDate(Instant.now());
        return post;
    }

    @Override
    public PostResponse toPostResponse(Post post) {
        PostResponse postResponse = mapper.map(post, PostResponse.class);
        postResponse.setCategory(categoryMapper.toCategoryResponseForPostView(post.getCategory()));
        postResponse.setAuthor(userMapper.toUserInfoResponseForPostView(post.getAuthor()));
        postResponse.setDate(Format.toLocalDate(post.getCreateDate()));
        return postResponse;
    }
}
