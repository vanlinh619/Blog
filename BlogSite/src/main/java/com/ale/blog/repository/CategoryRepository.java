package com.ale.blog.repository;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByLevelAndAuthor(CategoryLevel level, User author);
    Optional<Category> findFirstBySlugAndAuthor_Username(String slug, String username);
    Optional<Category> findCategoryByIdAndAuthor(Long id, User author);
}
