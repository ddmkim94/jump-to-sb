package com.ll.sbb.user;

public class SignupUsernameDuplicatedException extends RuntimeException{
    public SignupUsernameDuplicatedException(String message) {
        super(message);
    }
}
