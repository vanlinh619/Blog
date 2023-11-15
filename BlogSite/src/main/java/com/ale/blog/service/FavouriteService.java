package com.ale.blog.service;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.FavouriteRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;

public interface FavouriteService extends EntityService<Favourite, Long> {
    Favourite updateFavouritePost(@Nonnull User user, @Nonnull String postSlug);
    Boolean ifFavourite(@Nullable User user, @Nonnull Post post);
}
