package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;


    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable int id ,String content) {
        Question question = questionService.getQuestion(id);

        answerService.create(question, content);

        return "redirect:/question/detail/%s".formatted(id);
    }
}
