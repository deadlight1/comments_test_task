package com.example.test_task.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Column(nullable = false)
    private LocalDateTime time;
    private Boolean delivered;
    //id,comment_id,time,boolean delivered
}
