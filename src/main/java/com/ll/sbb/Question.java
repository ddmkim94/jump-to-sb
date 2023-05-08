package com.ll.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT") // 컬럼 타입: text
    private String content;

    // mappedBy 에는 해당 필드와 매핑되는 필드의 이름을 작성 @ManyToOne 필드
    // CascadeType.REMOVE 옵션은 부모 객체(Question)가 삭제될 때 자식 객체(Answer)도 함께 삭제하는 옵션이다.
    // 질문이 없으면 답변도 없기 때문에 질문 테이블이 부모, 답변 테이블이 자식 테이블이 된다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    private LocalDateTime createDate;
}