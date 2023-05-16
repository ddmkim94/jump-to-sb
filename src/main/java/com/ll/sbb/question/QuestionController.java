package com.ll.sbb.question;

import com.ll.sbb.answer.AnswerForm;
import com.ll.sbb.exception.DataNotFoundException;
import com.ll.sbb.user.SiteUser;
import com.ll.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
// 컨트롤러는 Repository가 있는지 몰라야 한다.
// 서비스는 웹브라우저라는것이 이 세상에 존재하는지 몰라야 한다.
// 세션은 컨트롤러에서 처리해야 한다.
// 리포지터리는 서비스를 몰라야 한다.
// 서비스는 컨트롤러를 몰라야 한다.
// DB는 리포지터리를 몰라야 한다.
// SPRING DATA JPA는 MySQL을 몰라야 한다.
// SPRING DATA JPA(리포지터리) -> JPA -> 하이버네이트 -> JDBC -> MySQL Driver -> MySQL
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable Long id) {
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.vote(question, siteUser);
        return "redirect:/question/detail/%d".formatted(id);
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue ="0") int page, @RequestParam(defaultValue = "") String kw) {
        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(Principal principal, @Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = userService.getUser(principal.getName());
        Question question = questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/detail/%d".formatted(question.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable Long id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        System.out.println(questionForm.getSubject());
        System.out.println(questionForm.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Principal principal, @Valid QuestionForm questionForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable Long id) {
        Question question = questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        questionService.delete(question);
        return "redirect:/";
    }
}
