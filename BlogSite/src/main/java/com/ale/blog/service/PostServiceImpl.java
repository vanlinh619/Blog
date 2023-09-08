package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.PostRequest;
import com.ale.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post createPostArticle(PostRequest postRequest) {
        return postMapper.toPost(postRequest);
    }
}
