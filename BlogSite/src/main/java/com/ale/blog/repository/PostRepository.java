package com.ale.blog.repository;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostBySlug(String slug);
    Optional<Post> findPostBySlugAndState(String slug, PostState state);
    @Query("select p from Post p where p.slug = :slug and p.author = :owner")
    Optional<Post> findPostBySlugAnOwner(String slug, User owner);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
    Page<Post> findAllByAuthorAndState(User author, PostState state, Pageable pageable);
    Page<Post> findAllByCategory(Category category, Pageable pageable);
    Page<Post> findAllByCategoryAndState(Category category, PostState state, Pageable pageable);
    Optional<Post> findPostByIdAndAuthor(Long id, User author);
    @Query("select p from Post p join Share s on p = s.post where p.category = :category and s.shareWith = :owner")
    Page<Post> findAllByCategoryAndShareWith(Category category, User owner, Pageable pageable);
    @Query("select p from Post p join Share s on p = s.post where p.author = :author and s.shareWith = :owner")
    Page<Post> findAllByAuthorAndShareWith(User author, User owner, Pageable pageable);
    Page<Post> findAllByAuthorNotAndState(User author, PostState state, Pageable pageable);
}
