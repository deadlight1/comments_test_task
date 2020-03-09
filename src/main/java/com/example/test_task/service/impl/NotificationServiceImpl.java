package com.example.test_task.service.impl;

import com.example.test_task.model.dto.NotificationDto;
import com.example.test_task.model.entity.Comment;
import com.example.test_task.model.entity.Notification;
import com.example.test_task.repository.NotificationRepository;
import com.example.test_task.service.NotificationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.test_task.util.BusinessLogic.doSomeWorkOnNotification;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper mapper;

    @Async("asyncExecutor")
    @Override
    public void createNotification(Comment savedComment) {
        Notification notification = Notification.builder()
                .comment(savedComment)
                .time(LocalDateTime.now())
                .delivered(true)
                .build();
        try {
            notificationRepository.save(notification);
            doSomeWorkOnNotification();
        } catch (RuntimeException r) {
            notification.setDelivered(false);
            notificationRepository.save(notification);
        }
    }

    @Override
    public Page<NotificationDto> getNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notification -> mapper.map(notification, NotificationDto.class));
    }
}
