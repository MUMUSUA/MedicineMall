package com.example.mall.user.exception;

public class UsernameException extends RuntimeException {


    public UsernameException() {
        super("存在相同的用户名");
    }
}