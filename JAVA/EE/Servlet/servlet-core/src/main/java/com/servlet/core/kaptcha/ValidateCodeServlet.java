package com.servlet.core.kaptcha;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 是在jsp中生成的验证码
@WebServlet("/ValidateCodeServlet")
public class ValidateCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中取出servlet生成的验证码text值
        // 解决mvn依赖kaptcha的问题: https://blog.csdn.net/lancelet223/article/details/78941489
        String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        System.out.println("生成的验证码："+kaptchaExpected);
        //获取用户页面输入的验证码
        String kaptchaReceived = request.getParameter("kaptcha");
        if(kaptchaReceived!=null) {
            System.out.println("收到的验证码:"+kaptchaReceived);
            if(kaptchaExpected.equalsIgnoreCase(kaptchaReceived)){
                response.getWriter().println("Succeed!");
            }
            else{
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println("你输入的验证码无效！");
            }
        }
    }
}
