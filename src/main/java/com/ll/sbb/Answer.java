package com.ll.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
}
