package com.example.mall.cart.conf;

import com.example.mall.cart.interceptor.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 21:31
 * @Description:
 */
@Configuration
public class MallWebConfig implements WebMvcConfigurer{
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
    }

}
