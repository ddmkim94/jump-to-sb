package com.ll.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionRepository.findAll();

        /**
         * Model 객체는 자바 클래스와 템플릿 간의 연결고리 역할을 한다.
         * Model 객체에 값을 담아두면 템플릿에서 사용 가능
          */

        model.addAttribute("questionList", questionList);

        return "question_list";
    }
}
