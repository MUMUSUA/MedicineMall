package com.example.mall.order.interceptor;

import com.example.common.constant.AuthServerConstant;
import com.example.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author:wxl
 * @date:2022/11/18
 **/
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MemberResponseVo attribute = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute != null) {
            loginUser.set(attribute);
            return true;
        } else {
            //没有登陆就去登陆
//            request.getSession().setAttribute("msg", "请先进行登陆");
//            response.sendRedirect("http://auth.mall.com:10009/login.html");
//            return false;

            loginUser.set(attribute);
            return true;
        }
    }
}
