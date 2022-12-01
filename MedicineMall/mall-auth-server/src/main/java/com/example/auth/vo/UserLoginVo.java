package com.example.auth.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVo implements Serializable {

    private String loginacct;
    private String password;
}
