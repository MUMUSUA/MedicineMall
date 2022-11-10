package com.example.mallcart.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.common.constant.CartConstant;
import com.example.common.vo.MemberResponseVo;
import com.example.mallcart.vo.UserInfoTo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
/**
 * @Author: marui
 * @Date: 2022/11/7
 * @Time: 19:43
 * @Description:在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求
 */

public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> ThreadLocal = new ThreadLocal<>();//ThreadLocal共享数据
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        //获得当前登录用户的信息
        MemberResponseVo memberResponseVo = (MemberResponseVo) session.getAttribute( "");
        if (memberResponseVo != null) {
            //用户登录了
            userInfoTo.setUserId(memberResponseVo.getId());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //user-key
                String name = cookie.getName();
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfoTo.setUserKey(cookie.getValue());


                }
            }

        }
        //如果没有临时用户一定分配一个临时用户
        if (StringUtils.isEmpty(userInfoTo.getUserKey())) {
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        //目标方法执行之前
        ThreadLocal.set(userInfoTo);
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        UserInfoTo userInfoTo = ThreadLocal.get();
        //持续的延长临时用户的过期时间
        Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
        cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
        cookie.setDomain("mall.com");
        response.addCookie(cookie);
    }
}
