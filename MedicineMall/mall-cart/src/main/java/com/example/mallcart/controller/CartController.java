package com.example.mallcart.controller;

import com.example.mallcart.interceptor.CartInterceptor;
import com.example.mallcart.vo.UserInfoTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: marui
 * @Date: 2022/11/7
 * @Time: 16:41
 * @Description:
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    //得到用户信息
   UserInfoTo userInfoTo = CartInterceptor.ThreadLocal.get();
}

