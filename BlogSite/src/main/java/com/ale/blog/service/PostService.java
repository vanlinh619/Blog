package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest, User author);
    Post getPostBySlug(String slug, @Nullable User owner);
    Page<Post> findAllByAuthor(User author, QueryRequest queryRequest);
    Page<Post> findAllByAuthor(
            @Nullable User owner,
            @Nonnull User author,
            @Nonnull PostState state,
            @Nonnull QueryRequest queryRequest
    );
    Page<Post> findAllByCategory(Category category, QueryRequest queryRequest);
    Page<Post> findAllByCategory(
            @Nonnull Category category,
            @Nullable User owner,
            @Nonnull User author,
            @Nonnull PostState state,
            @Nonnull QueryRequest queryRequest
    );

    //Api authorize
    Post getByIdAndAuthor(Long id, User author);
    Post save(Post post);
    Optional<Post> postWithPermission(Post post, @Nullable User owner);

    void increaseView(Long id);
    void increaseComment(Long id);
    void increaseFavourite(Long id);
}
