package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Notification;
import com.ale.blog.handler.mapper.pojo.response.NotificationResponse;
import com.ale.blog.handler.utils.Format;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponse toNotificationResponse(Notification notification) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .id(notification.getId())
                .seen(notification.getSeen())
                .type(notification.getType().name())
                .updateDate(Format.toTimeDifference(notification.getUpdateDate()))
                .username(Format.toUsername(notification.getActors()))
                .count((long) notification.getActors().size())
                .build();
        switch (notification.getType()) {
            case FAVOURITE_POST -> {
                notificationResponse.setPostSlug(notification.getPost().getSlug());
                notificationResponse.setContent(notification.getPost().getTitle());
            }
            case COMMENT_POST, RELY_COMMENT -> {
                if(notification.getComment().getSuperParent() != null) {
                    notificationResponse.setSupperCommentId(notification.getComment().getSuperParent().getId());
                }
                notificationResponse.setCommentId(notification.getComment().getId());
                notificationResponse.setPostSlug(notification.getPost().getSlug());
                notificationResponse.setContent(notification.getComment().getContent());
            }
        }
        return notificationResponse;
    }
}
