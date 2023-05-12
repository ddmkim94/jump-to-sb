package com.ll.sbb.answer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AnswerForm {

    @NotEmpty(message="내용은 필수항목입니다.")
    @Size(max = 200)
    private String content;
}
