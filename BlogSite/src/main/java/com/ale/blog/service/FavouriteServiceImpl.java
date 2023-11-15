package com.ale.blog.service;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.FavouriteState;
import com.ale.blog.repository.FavouriteRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
        return favouriteRepository.findFavouriteByUserAndPost(user, post)
                .map(favourite -> {
                    switch (favourite.getState()) {
                        case PERSIST -> {
                            favourite.setState(FavouriteState.DELETED);
                            postService.decreaseFavourite(post.getId());
                        }
                        case DELETED -> {
                            favourite.setState(FavouriteState.PERSIST);
                            postService.increaseFavourite(post.getId());
                        }
                    }
                    favouriteRepository.save(favourite);
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
                    postService.increaseFavourite(post.getId());
                    return favourite;
                });
    }

    @Override
    public Boolean ifFavourite(@Nullable User user, @Nonnull Post post) {
        return Optional.ofNullable(user)
                .flatMap(us -> favouriteRepository.findFavouriteByUserAndPostAndState(us, post, FavouriteState.PERSIST))
                .isPresent();
    }

    @Override
    public Class<Favourite> getEntityClass() {
        return Favourite.class;
    }
}
