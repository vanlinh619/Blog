package com.ale.blog.service;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import com.ale.blog.handler.mapper.pojo.request.NotificationObjectWrapper;
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
    private final NotificationService notificationService;

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public Optional<Favourite> persistOrDelete(@Nonnull User user, @Nonnull String postSlug) {
        Post post = postService.getPostBySlug(postSlug, user);
        return favouriteRepository.findFirstByUserAndPost(user, post)
                .map(favourite -> {
                    favouriteRepository.delete(favourite);
                    notificationService.deleteOrUpdateNotification(
                            post.getAuthor(),
                            user,
                            NotificationType.FAVOURITE_POST,
                            NotificationObjectWrapper.builder().post(post).build()
                    );
                    post.setFavourite(post.getFavourite() - 1);
                    postService.save(post);
                    return Optional.<Favourite>empty();
                })
                .orElseGet(() -> {
                    Favourite favourite = Favourite.builder()
                            .post(post)
                            .timestamp(Instant.now())
                            .user(user)
                            .build();
                    favouriteRepository.save(favourite);
                    notificationService.upsertNotification(
                            post.getAuthor(),
                            user,
                            NotificationType.FAVOURITE_POST,
                            NotificationObjectWrapper.builder().post(post).build()
                    );
                    post.setFavourite(post.getFavourite() + 1);
                    postService.save(post);
                    return Optional.of(favourite);
                });
    }

    @Override
    public Boolean ifFavourite(@Nullable User user, @Nonnull Post post) {
        return Optional.ofNullable(user)
                .flatMap(us -> favouriteRepository.findFavouriteByUserAndPost(us, post))
                .isPresent();
    }

    @Override
    public Class<Favourite> getEntityClass() {
        return Favourite.class;
    }
}
