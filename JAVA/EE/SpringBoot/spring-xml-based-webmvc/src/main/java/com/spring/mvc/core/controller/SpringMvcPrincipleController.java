package com.spring.mvc.core.controller;

import com.spring.mvc.core.model.Department;
import com.spring.mvc.core.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
public class SpringMvcPrincipleController {

    public static final String SUCCESS="spring_mvc_principle";

    //1.简单的谈一下SpringMVC的工作流程

    //处理模型数据方式一：将方法的返回值设置为ModelAndView
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        //1.创建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        //2.设置模型数据，最终会放到request域中
        mav.addObject("user", "admin");
        //3.设置视图
        mav.setViewName("spring_mvc_principle");
        return mav;
    }
    /*
     * ★处理模型数据方式二：方法的返回值仍是String类型，在方法的入参中传入Map、Model或者ModelMap
     * 	不管将处理器方法的返回值设置为ModelAndView还是在方法的入参中传入Map、Model或者ModelMap，
     *  SpringMVC都会转换为一个ModelAndView对象
     */
    @RequestMapping("/testMap")
    public String testMap(Map<String , Object> map) {
        //向Map中添加模型数据，最终会自动放到request域中
        map.put("user", new Employee(1, "你的名字", "aaa@qq.com", new Department(101, "xx部门")));
        return SUCCESS;
    }

}
