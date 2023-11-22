package com.ale.blog.repository;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Optional<Favourite> findFirstByUserAndPost(User user, Post post);
    Optional<Favourite> findFavouriteByUserAndPost(User user, Post post);
}
