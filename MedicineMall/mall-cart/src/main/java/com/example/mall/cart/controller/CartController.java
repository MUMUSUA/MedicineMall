package com.example.mall.cart.controller;

import com.alibaba.nacos.shaded.org.checkerframework.common.reflection.qual.GetMethod;
import com.example.common.constant.AuthServerConstant;
import com.example.mall.cart.To.UserInfoTo;
import com.example.mall.cart.interceptor.CartInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.AuthResources_ja;

import javax.servlet.http.HttpSession;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 15:29
 * @Description:
 */
@Controller
public class CartController {
    @GetMapping("/cart.html")
    public String CartListPage(){
        // ThreadLocal快速得到用户信息 userId,userKey
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
//        System.out.println(userInfoTo);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(){

    return "success";
    }

}

