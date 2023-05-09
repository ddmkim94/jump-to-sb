package com.ll.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        // QuestionController 의 /question/list 으로 리다이렉트!
        return "redirect:/question/list";
    }

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String multiply(@RequestParam(defaultValue = "2") int dan, @RequestParam(defaultValue = "9") int limit) {
        System.out.println("dan = " + dan);
        System.out.println("limit = " + limit);
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(dan, i, dan * i))
                .collect(Collectors.joining("<br/>"));
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }

    @GetMapping("addPersonOldWay")
    @ResponseBody
    Person addPersonOldWay(int id, int age, String name) {
        Person p = new Person(id, age, name);

        return p;
    }

    @GetMapping("addPerson")
    @ResponseBody
    Person addPerson(Person p) {
        return p;
    }

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    Person addPersonPathVariable(Person p) {
        return p;
    }

    @AllArgsConstructor
    @Getter
    static class Person {
        private int id;
        private int age;
        private String name;
    };
}
