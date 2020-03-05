package com.example.test_task.exception;

import com.example.test_task.model.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CantCreateCommentException extends RuntimeException {

    private CommentDto commentDto;

    public CantCreateCommentException(CommentDto commentDto) {
        super();
        this.commentDto = commentDto;
    }
}
