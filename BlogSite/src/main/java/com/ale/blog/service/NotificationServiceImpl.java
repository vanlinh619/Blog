package com.ale.blog.service;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import com.ale.blog.handler.mapper.pojo.request.NotificationObjectWrapper;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.repository.NotificationRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public Optional<Notification> upsertNotification(
            @Nonnull User receiver,
            @Nonnull User actor,
            @Nonnull NotificationType type,
            @Nonnull NotificationObjectWrapper objectWrapper
    ) {
        if (receiver.equals(actor)) return Optional.empty();
        return Optional.ofNullable(type.equals(NotificationType.FAVOURITE_POST) ? type : null)
                .flatMap(t -> notificationRepository.findFirstByReceiverAndTypeAndPostAndSeenIsFalse(
                        receiver, t, objectWrapper.getPost()
                ))
                .map(notification -> {
                    notification.getActors().add(actor);
                    notification.setUpdateDate(Instant.now());
                    return notification;
                })
                .or(() -> {
                    Notification notification = Notification.builder()
                            .receiver(receiver)
                            .actors(List.of(actor))
                            .type(type)
                            .post(objectWrapper.getPost())
                            .comment(objectWrapper.getComment())
                            .createDate(Instant.now())
                            .updateDate(Instant.now())
                            .seen(false)
                            .build();
                    return Optional.of(notification);
                })
                .map(notification -> {
                    notificationRepository.save(notification);
                    return notification;
                });
    }

    @Override
    public Boolean deleteOrUpdateNotification(
            @Nonnull User receiver,
            @Nonnull User actor,
            @Nonnull NotificationType type,
            @Nonnull NotificationObjectWrapper objectWrapper
    ) {
        if (receiver.equals(actor)) return false;
        return notificationRepository.findFirstByReceiverAndTypeAndPostAndCommentAndActorsContaining(
                        receiver, type, objectWrapper.getPost(), objectWrapper.getComment(), actor
                )
                .map(notification -> {
                    notification.getActors().remove(actor);
                    if (notification.getActors().isEmpty()) {
                        notificationRepository.delete(notification);
                    } else {
                        notificationRepository.save(notification);
                    }
                    return actor;
                })
                .isPresent();
    }

    @Override
    public Page<Notification> loadNotification(@Nonnull User receiver, @Nonnull QueryRequest queryRequest) {
        return notificationRepository.findAllByReceiver(receiver, Convert.pageRequest(queryRequest));
    }

    @Override
    public Integer countNewNotification(@Nonnull User receiver) {
        return notificationRepository.countNewNotification(receiver);
    }

    @Override
    public void seen(@Nonnull User receiver, @Nonnull List<Long> ids) {
        notificationRepository.seen(receiver, ids);
    }

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }
}
