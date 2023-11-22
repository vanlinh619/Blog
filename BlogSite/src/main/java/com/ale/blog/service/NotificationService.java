package com.ale.blog.service;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import com.ale.blog.handler.mapper.pojo.request.NotificationObjectWrapper;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NotificationService extends EntityService<Notification, Long> {
    Optional<Notification> upsertNotification(
            @Nonnull User receiver,
            @Nonnull User actor,
            @Nonnull NotificationType type,
            @Nonnull NotificationObjectWrapper objectWrapper
    );
    Boolean deleteOrUpdateNotification(
            @Nonnull User receiver,
            @Nonnull User actor,
            @Nonnull NotificationType type,
            @Nonnull NotificationObjectWrapper objectWrapper
    );
    Page<Notification> loadNotification(@Nonnull User receiver, @Nonnull QueryRequest queryRequest);

    Integer countNewNotification(@Nonnull User receiver);
    void seen(Notification notification);
}
