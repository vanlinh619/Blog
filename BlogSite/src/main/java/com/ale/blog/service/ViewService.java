package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.View;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;


public interface ViewService extends EntityService<View, Long> {
    void increaseView(@Nullable User user, @Nonnull Post post, @Nonnull HttpServletRequest request);
}
