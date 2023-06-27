package com.springboot.web.restful.curd.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 自定义的LocaleResolver,也就是在http请求上携带区域信息,在这里解析
 */
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");

        // 这里是获取操作系统OS默认的语言环境(注意和浏览器使用的语言环境要区分)
        Locale locale = Locale.getDefault();

        // 如果url带了区域信息参数,就用我url中指定的
        if (!StringUtils.isEmpty(l)) {
            // l格式为: zh_CN
            String[] arr = l.split("_");
            // 这里是核心,构建了我们自己的Locale,作用是根据url中携带的区域信息参数来决定
            // 国际化为什么语言环境,而不是像AcceptHeaderLocaleResolver那样依然于http的请求头
            locale = new Locale(arr[0], arr[1]);
        }

        // 如果url没有携带区域信息参数,直接使用操作系统默认的语言locale
        // 假如os默认为中文语言,如果没有带区域信息参数,即使你的浏览器语言是英文的,
        // 页面效果依然会是os默认的中文.
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {}
}
