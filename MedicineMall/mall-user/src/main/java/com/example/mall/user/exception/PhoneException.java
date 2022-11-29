package com.example.mall.user.exception;

public class PhoneException extends RuntimeException{
    public PhoneException() {
        super("存在相同的手机号");
    }
}
