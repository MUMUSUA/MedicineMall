package com.example.mall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:wxl
 * @date:2022/11/21
 **/

@Configuration
public class FeignConfig {

//    @Bean("requestInterceptor")
//    public RequestInterceptor requestInterceptor() {
//
//        RequestInterceptor requestInterceptor = new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate template) {
//
//                //1、使用RequestContextHolder拿到刚进来的请求数据
//                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                System.out.println("RequestInterceptor线程..." + Thread.currentThread().getId());
//                if (requestAttributes != null) {
//                    //老请求
//                    HttpServletRequest request = requestAttributes.getRequest();
//
//                    if (request != null) {
//                        //2、同步请求头的数据（主要是cookie）
//                        //把老请求的cookie值放到新请求上来，进行一个同步
//                        String cookie = request.getHeader("Cookie");
//                        template.header("Cookie", cookie);
//                    }
//                }
//                System.out.println("feign远程之前先进行requestInterceptor.apply");
//            }
//        };
//
//        return requestInterceptor;
//    }


    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                //1、拿到刚进来的这个老请求
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {

                    System.out.println("RequestInterceptor线程。。。" + Thread.currentThread().getId());
                    HttpServletRequest request = attributes.getRequest();//老请求
                    if (request != null) {
                        //同步请求头信息，cookie
                        template.header("Cookie", request.getHeader("Cookie"));
                        //给新请求同步老请求的cookie
                        System.out.println("feign远程之前先这里 apply 方法");
                    }
                }
            }
        };
    }

}
