package com.ale.blog.repository;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findNotificationByAuthorAndObjectId(User author, String objectId);
}
