package com.ll.sbb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpa0() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);

        questionRepository.disableForeignKeyChecks();
        questionRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    @Test
    void testJpa1() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);  // 두번째 질문 저장
    }

    @Test
    void testJpa2() {
        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        assertThat(all.get(0).getSubject()).isEqualTo("sbb가 무엇인가요?");
    }


    @Test
    void testJpa3() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(q.getId()).isEqualTo(1);
    }


    @Test
    void testJpa4() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(q.getId()).isEqualTo(1);
    }


    @Test
    void testJpa5() {
        List<Question> list = questionRepository.findBySubjectLike("%sbb%");
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void testJpa6() {
        Optional<Question> oq = questionRepository.findById(1);

        assertThat(oq).isPresent();
        Question q = oq.get();

        q.setSubject("제목 수정해보기");
        questionRepository.save(q);
    }

    @Test
    void testJpa7() {
        assertThat(questionRepository.count()).isEqualTo(2);
        Optional<Question> oq = questionRepository.findById(1);
        assertThat(oq).isPresent();

        Question q = oq.get();

        questionRepository.delete(q);
        assertThat(questionRepository.count()).isEqualTo(1);
    }
}