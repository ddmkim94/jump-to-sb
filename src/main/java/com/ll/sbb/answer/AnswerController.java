package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import com.ll.sbb.user.SiteUser;
import com.ll.sbb.user.UserRepository;
import com.ll.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;


    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        SiteUser siteUser = userService.getUser(principal.getName());
        answerService.create(question, answerForm.getContent(), siteUser);

        return "redirect:/question/detail/%s".formatted(id);
    }
}
