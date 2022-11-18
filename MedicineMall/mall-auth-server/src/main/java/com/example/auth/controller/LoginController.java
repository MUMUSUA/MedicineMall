package com.example.auth.controller;
import com.example.auth.feign.ThirdPartyFeignService;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
public class LoginController {

//    @Autowired
    @Resource
    ThirdPartyFeignService thirdPartyFeignService;

    /**
     * 发送一个请求直接跳转到一个页面
     * SpringMVC viewcontroller;将请求和页面映射过来
     *
     */
//    @GetMapping("/login.html")
//    public String loginPage(){
//        return "login";
//    }
//
//    @GetMapping("/reg.html")
//    public String regPage(){
//        return "reg";
//    }

    @ResponseBody
    @GetMapping("/send_sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone){

        //1、接口防刷

        //2、验证码的再次校验
        String code = UUID.randomUUID().toString().substring(0,5);


        thirdPartyFeignService.sendCode(phone,code);
        return R.ok();
    }

}
