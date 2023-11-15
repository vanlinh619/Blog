package com.ale.blog.service;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.FavouriteState;
import com.ale.blog.repository.FavouriteRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final PostService postService;

    @Override
    public Favourite updateFavouritePost(@Nonnull User user, @Nonnull String postSlug) {
        Post post = postService.getPostBySlug(postSlug, user);
        return favouriteRepository.findFirstByUserAndPost(user, post)
                .map(favourite -> {
                    switch (favourite.getState()) {
                        case PERSIST -> {
                            favourite.setState(FavouriteState.DELETED);
                            post.setFavourite(post.getFavourite() - 1);
                        }
                        case DELETED -> {
                            favourite.setState(FavouriteState.PERSIST);
                            post.setFavourite(post.getFavourite() + 1);
                        }
                    }
                    favouriteRepository.save(favourite);
                    postService.save(post);
                    return favourite;
                })
                .orElseGet(() -> {
                    Favourite favourite = Favourite.builder()
                            .post(post)
                            .timestamp(Instant.now())
                            .user(user)
                            .state(FavouriteState.PERSIST)
                            .build();
                    favouriteRepository.save(favourite);
                    post.setFavourite(post.getFavourite() + 1);
                    postService.save(post);
                    return favourite;
                });
    }

    @Override
    public Boolean ifFavourite(@Nullable User user, @Nonnull Post post) {
        return Optional.ofNullable(user)
                .flatMap(us -> favouriteRepository.findFirstByUserAndPostAndState(us, post, FavouriteState.PERSIST))
                .isPresent();
    }

    @Override
    public Class<Favourite> getEntityClass() {
        return Favourite.class;
    }
}
