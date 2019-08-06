package com.springboot.exception.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 将spring boot应用打包成jar或者war,对应的模板文件应该放在哪里?
 * https://www.logicbig.com/tutorials/spring-framework/spring-boot/boot-serve-dynamic.html
 *
 * 注意: 在打成jar的时候,不要把模板文件放在src/main/webapp下,尽管这个目录在传统的war中是标准的.
 * 但是打成jar的时候,大多数构建工具会忽略这个目录,会导致应用无法找到模板.
 *
 * servlet3中要放在src/main/resources/META-INF/resources/中.
 *
 * 如果打成war,JSPs的位置可有有2种选择: 一种是src/main/resources/META-INF/resources/.
 * 另一种是src/main/webapp/
 */
@Controller
public class UseJspController {

    @RequestMapping("/welcome")
    public ModelAndView welcome(){

        //如果没有在application.properties中定义了
        // spring.mvc.view.prefix和spring.mvc.view.suffix
        //则需要明确地指出其视图文件名
        //return new ModelAndView("/WEB-INF/jsp/welcome.jsp","now",new Date());

        //定义了suffix和prefix之后，可以简单地直接使用不包容路径和后缀的视图文件名
        return new ModelAndView("welcome","now",new Date());
    }
}

