package com.ll.sbb.question;

import com.ll.sbb.exception.DataNotFoundException;
import com.ll.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw, String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            case "NEW" -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameOrAnswerList_ContentContainsOrAnswerList_Author_Username(kw, kw, kw, kw, kw, pageable);
    }

    public List<Question> getList() {
        return questionRepository.findAllByOrderByIdDesc();
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("%d번 게시글 not found!".formatted(id)));
    }

    public Question create(String subject, String content, SiteUser author) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setAuthor(author);
        question.setCreateDate(LocalDateTime.now());

        return questionRepository.save(question);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());

        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        questionRepository.save(question);
    }
}
