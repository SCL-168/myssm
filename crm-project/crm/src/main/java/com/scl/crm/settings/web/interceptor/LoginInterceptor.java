package com.scl.crm.settings.web.interceptor;

import com.scl.crm.commons.contants.Contants;
import com.scl.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*拦截器*/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session,判断有没有登录成功
        HttpSession session = request.getSession();
        User user =(User) session.getAttribute(Contants.SESSION_USER);
        //判断用户信息是否为空
        if(user==null){
            //没有登录，跳转到登录页面
            response.sendRedirect(request.getContextPath());//重定向时，URL必须要加项目的名称；
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
