package com.ale.blog.repository;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Notification;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findFirstByReceiverAndTypeAndPostAndCommentAndActorsContaining(
            User receiver,
            NotificationType type,
            Post post,
            Comment comment,
            User actor
    );
    Optional<Notification> findFirstByReceiverAndTypeAndPostAndSeenIsFalse(
            User receiver,
            NotificationType type,
            Post post
    );
    Page<Notification> findAllByReceiver(User receiver, Pageable pageable);
    @Query("select count(n) from Notification n join n.actors a where n.receiver = :receiver and n.seen = false")
    Integer countNewNotification(User receiver);
}
