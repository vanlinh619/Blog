package com.ale.blog.service;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import jakarta.annotation.Nonnull;

import java.util.Optional;

public interface NotificationService extends EntityService<Notification, Long> {
    Optional<Notification> addNotification(@Nonnull User owner, @Nonnull User author, @Nonnull NotificationType type, @Nonnull String objectId);
    Boolean deleteNotification(@Nonnull User author, @Nonnull String objectId);
}
