package com.ale.blog.service;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import com.ale.blog.repository.NotificationRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final BroadcastService broadcastService;

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }

    @Override
    public Optional<Notification> addNotification(@Nonnull User owner, @Nonnull User author, @Nonnull NotificationType type, @Nonnull String objectId) {
        if (owner.equals(author)) return Optional.empty();
        Notification notification = Notification.builder()
                .author(author)
                .owner(owner)
                .objectId(objectId)
                .type(type)
                .createDate(Instant.now())
                .seen(false)
                .build();
        notificationRepository.save(notification);
        return Optional.of(notification);
    }

    @Override
    public Boolean deleteNotification(@Nonnull User author, @Nonnull String objectId) {
        return notificationRepository.findNotificationByAuthorAndObjectId(author, objectId)
                .map(notification -> {
                    notificationRepository.delete(notification);
                    return notification;
                })
                .isPresent();
    }
}
