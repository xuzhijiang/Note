package com.legacy.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

// 第一步:http://localhost:8080/test/
// 第二步: http://localhost:8080/test/?locale=fr
// 第三步: http://localhost:8080/test/
// 正如您所看到的那样，第三步中，我们没有在客户端请求中传递区域设置信息，
// 但我们的应用程序仍然识别用户区域设置(为fr)。 这是因为我们在spring bean配置文件
// 中配置了CookieLocaleResolver other。您可以检查浏览器cookie数据以进行确认。
// 也就是第三步请求的时候，携带了cookie的信息中声明了位置信息.
@Controller
public class i18nController {

    private static final Logger logger = LoggerFactory.getLogger(i18nController.class);

    @RequestMapping(value = "/i18n", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);
        return "i18n";
    }

}