package com.example.test_task.service.impl;

import com.example.test_task.exception.CantCreateCommentException;
import com.example.test_task.model.dto.CommentDto;
import com.example.test_task.model.dto.NotificationDto;
import com.example.test_task.model.entity.Comment;
import com.example.test_task.model.entity.Notification;
import com.example.test_task.repository.CommentRepository;
import com.example.test_task.repository.NotificationRepository;
import com.example.test_task.service.MainService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.example.test_task.util.BusinessLogic.doSomeWorkOnCommentCreation;
import static com.example.test_task.util.BusinessLogic.doSomeWorkOnNotification;

@Service
@AllArgsConstructor
public class MainServiceImpl implements MainService {

    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public CommentDto createComment(CommentDto comment) {
        Comment savedComment = commentRepository.save(mapper.map(comment, Comment.class));
        try {
            doSomeWorkOnCommentCreation();
        } catch (RuntimeException r) {
            throw new CantCreateCommentException(mapper.map(savedComment, CommentDto.class));
        }
        createNotification(savedComment);
        return mapper.map(savedComment, CommentDto.class);
    }

    private void createNotification(Comment savedComment) {
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
    public Page<CommentDto> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(comment -> mapper.map(comment, CommentDto.class));
    }

    @Override
    public Page<NotificationDto> getNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notification -> mapper.map(notification, NotificationDto.class));
    }
}
