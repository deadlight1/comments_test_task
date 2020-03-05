package com.example.test_task.repository;

import com.example.test_task.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countNotificationByDeliveredTrue();

    Optional<Notification> findByCommentId(long id);
}
