package com.ale.blog.repository;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.ImageState;
import com.ale.blog.entity.state.ImageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    Optional<Image> findFirstByIdAndState(UUID uuid, ImageState state);
    Page<Image> findAllByAuthorAndStateAndType(User author, ImageState state, ImageType type, Pageable pageable);
    Optional<Image> findFirstByIdAndStateAndAuthor(UUID id, ImageState state, User author);
    @Transactional
    @Modifying
    @Query("update Image i set i.state = :state where i.id = :id")
    void updateStateById(UUID id, ImageState state);
    Optional<Image> findFirstByHashAndAuthorAndType(String hash, User author, ImageType type);
    Optional<Image> findFirstByAuthorAndStateAndType(User author, ImageState state, ImageType type);
}
