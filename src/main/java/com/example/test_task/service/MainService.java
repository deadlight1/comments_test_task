package com.example.test_task.service;

import com.example.test_task.model.dto.CommentDto;
import com.example.test_task.model.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainService {
    CommentDto createComment(CommentDto comment);

    Page<CommentDto> getComments(Pageable pageable);

    Page<NotificationDto> getNotifications(Pageable pageable);
}
