package com.example.mall.cart.interceptor;

import com.example.common.constant.AuthServerConstant;
import com.example.common.constant.CartConstant;
import com.example.common.vo.MemberResponseVo;
import com.example.mall.cart.To.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 15:58
 * @Description:在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求
 */

public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        MemberResponseVo member = (MemberResponseVo) session.getAttribute((AuthServerConstant.LOGIN_USER));
        if (member != null) {
            userInfoTo.setUserId(member.getId());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) ;
                userInfoTo.setUserKey(cookie.getValue());
            }
        }
        // 目标方法执行之前
        threadLocal.set(userInfoTo);
        return true;
    }
}
