package com.example.test_task.service.impl;

import com.example.test_task.exception.CantCreateCommentException;
import com.example.test_task.model.dto.CommentDto;
import com.example.test_task.model.entity.Comment;
import com.example.test_task.repository.CommentRepository;
import com.example.test_task.service.CommentService;
import com.example.test_task.service.NotificationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.test_task.util.BusinessLogic.doSomeWorkOnCommentCreation;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public CommentDto createComment(CommentDto comment) {
        Comment savedComment = commentRepository.save(mapper.map(comment, Comment.class));
        try {
            doSomeWorkOnCommentCreation();
        } catch (RuntimeException r) {
            throw new CantCreateCommentException(mapper.map(savedComment, CommentDto.class));
        }
        notificationService.createNotification(savedComment);
        return mapper.map(savedComment, CommentDto.class);
    }

    @Override
    public Page<CommentDto> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(comment -> mapper.map(comment, CommentDto.class));
    }


}
