package com.example.mallcart.config;

import com.example.mallcart.interceptor.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: marui
 * @Date: 2022/11/7
 * @Time: 21:45
 * @Description:
 */
@Configuration
public class MallWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterceptor())//注册拦截器
                .addPathPatterns("/**");
    }

}
