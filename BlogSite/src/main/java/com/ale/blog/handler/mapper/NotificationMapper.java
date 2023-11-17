package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Notification;
import com.ale.blog.handler.mapper.pojo.response.NotificationResponse;

public interface NotificationMapper extends Mapper{
    NotificationResponse toNotificationResponse(Notification notification);
}
