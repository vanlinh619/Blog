package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest, User author);
    Post getPostBySlug(String slug);
    Page<Post> findAllByUsername(String username, QueryRequest queryRequest);
    Page<Post> findAllByAuthor(User author, QueryRequest queryRequest);
    Page<Post> findAllByCategory(Category category, QueryRequest queryRequest);
}
