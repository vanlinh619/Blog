package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    @Override
    public Post createPostArticle(PostRequest postRequest) {
        Post post = postMapper.toPost(postRequest);
        post.setAuthor(userService.getById(UUID.fromString(postRequest.getAuthor())));
        postRepository.save(post);
        return post;
    }
}
