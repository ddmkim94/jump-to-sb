package com.ll.sbb;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void createSampleData(QuestionRepository questionRepository) {
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
    }

    private void createSampleData() {
        createSampleData(questionRepository);
    }

    private void clearData() {
        questionRepository.disableForeignKeyChecks();
        questionRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    @Test
    @DisplayName("question 저장이 성공해야 한다.")
    @Rollback(false)
    void saveQuestion__Test() {
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

        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(302);
    }

    @Test
    @DisplayName("question 삭제가 성공해야 한다.")
    void deleteQuestion__Test() {
        assertThat(questionRepository.count()).isEqualTo(2L);

        Question question = questionRepository.findById(1L).get();
        questionRepository.delete(question);

        assertThat(questionRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("question 수정이 성공해야 한다.")
    void updateQuestion__Test() {
        Question question = questionRepository.findById(1L).get();
        question.setSubject("제목 변경!");
        question.setContent("내용도 변경");

        questionRepository.save(question);

        assertThat(question.getSubject()).isEqualTo("제목 변경!");
        assertThat(question.getContent()).isEqualTo("내용도 변경");

        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("제목으로 question을 조회할 수 있다.")
    void findBySubject__Test() {
        Question question = questionRepository.findBySubject("sbb가 무엇인가요?");

        assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @DisplayName("제목과 내용으로 question을 조회할 수 있다.")
    void findBySubjectAndContent__Test() {
        Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

        assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
        assertThat(question.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    @DisplayName("LIKE 연산자와 제목으로 question을 조회할 수 있다.")
    @Rollback(false)
    void findBySubjectLike__Test() {
        List<Question> questionList = questionRepository.findBySubjectLike("sbb%");

        assertThat(questionList.size()).isEqualTo(1);
        assertThat(questionList.get(0).getSubject()).isEqualTo("sbb가 무엇인가요?");
        assertThat(questionList.get(0).getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    @Rollback(false)
    void createManySampleData() {
        boolean run = true;

        if (run == false) return;

        IntStream.rangeClosed(3, 300).forEach(id -> {
            Question q = new Question();
            q.setSubject("%d번 질문".formatted(id));
            q.setContent("%d번 질문의 내용".formatted(id));
            q.setCreateDate(LocalDateTime.now());
            questionRepository.save(q);
        });
    }
}
