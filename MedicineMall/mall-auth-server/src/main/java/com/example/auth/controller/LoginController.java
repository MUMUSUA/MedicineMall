//package com.example.auth.controller;
//
//
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//
//@EnableDiscoveryClient
//@RestController
//@RefreshScope
//public class  LoginController{
//    /**
//     * 访问登录页面
//     * 登录状态自动跳转首页
//     */
//    @GetMapping(value = "/login.html")
//    public String loginPage(HttpSession session) {
//        // 判断是否登录状态
////        Object attribute = session.getAttribute(AuthConstant.LOGIN_USER);
////        if (attribute == null) {
////            // 未登录，返回登录页资源
//            return "login";
////        } else {
////            // 已登录
////            return "redirect:http://gulimall.com";
////        }
//    }


//        }