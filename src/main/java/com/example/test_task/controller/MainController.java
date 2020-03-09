package com.example.test_task.controller;

import com.example.test_task.model.dto.CommentDto;
import com.example.test_task.model.dto.NotificationDto;
import com.example.test_task.service.CommentService;
import com.example.test_task.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class MainController {

    private final CommentService commentService;
    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CommentDto createComment(@RequestBody @Valid CommentDto comment) {
        return commentService.createComment(comment);
    }

    @GetMapping
    public Page<CommentDto> getComments(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.getComments(pageable);
    }


    @GetMapping("/notifications")
    public Page<NotificationDto> getNotifications(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return notificationService.getNotifications(pageable);
    }
}
