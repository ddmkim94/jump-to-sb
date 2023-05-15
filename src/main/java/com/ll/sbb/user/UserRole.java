package com.ll.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("일반사용자"), ADMIN("관리자");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
