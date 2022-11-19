package com.scl.crm.settings.web.controller;


import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.domain.ReturnObject;
import com.scl.crm.commons.utils.DateUtils;
import com.scl.crm.settings.domain.User;
import com.scl.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    //自动注入调用service
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";
    }


    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd,
                        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        //调用service层方法查询用户
        User user = userService.queryUserByLoginActAndPwd(map);

        //调用实体类
        ReturnObject returnObject=new ReturnObject();
        //根据查询结果判断用户信息
        if (user == null) {
            //用户名或密码有误
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("用户名或密码有误");
        } else {
               //调用时间格式化工具类
            /*
            String dateTime = DateUtils.formateDateTime(new Date());
            String time = user.getExpireTime();*/

            if (DateUtils.formateDateTime(new Date()).compareTo(user.getExpireTime()) > 0) {
                //过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("用户登录信息过期");
            } else if ("0".equals(user.getLockState())) {
                //用户状态被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("用户状态被锁定");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                //ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("IP受限");
            }else{
                //登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //把用户信息放到session中
                session.setAttribute(Contants.SESSION_USER, user);
                //创建cookie,用户存储用户登录信息
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else {
                    //删除上次存储的cookie
                    Cookie c1 = new Cookie("loginAct", "1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", "1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }


    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //删除cookie
        Cookie c1 = new Cookie("loginAct", "1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", "1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();//销毁session
        return "redirect:/";
    }
}
