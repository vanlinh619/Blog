package com.ale.blog.repository;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
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
    Optional<Post> findFirstBySlug(String slug);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
    Page<Post> findAllByCategoriesContaining(Category category, Pageable pageable);
    @Transactional
    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    void increaseView(Long id);
}
