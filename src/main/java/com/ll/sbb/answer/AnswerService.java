package com.ll.sbb.answer;

import com.ll.sbb.exception.DataNotFoundException;
import com.ll.sbb.question.Question;
import com.ll.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        question.addAnswer(answer);

        return answerRepository.save(answer);
    }

    public Answer getAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("답변을 찾을 수 없습니다."));
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());

        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }
}
