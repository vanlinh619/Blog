package com.ale.blog.repository;

import com.ale.blog.entity.SlugId;
import com.ale.blog.entity.state.SlugType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlugIdRepository extends JpaRepository<SlugId, Long> {
    Optional<SlugId> findFirstByOrderById();
    Optional<SlugId> findSlugIdByType(SlugType type);
}
