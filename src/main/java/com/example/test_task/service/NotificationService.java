package com.example.test_task.service;

import com.example.test_task.model.dto.NotificationDto;
import com.example.test_task.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async("asyncExecutor")
    void createNotification(Comment savedComment);

    Page<NotificationDto> getNotifications(Pageable pageable);
}
