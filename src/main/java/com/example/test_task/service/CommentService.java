package com.example.test_task.service;

import com.example.test_task.model.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDto createComment(CommentDto comment);

    Page<CommentDto> getComments(Pageable pageable);

}
