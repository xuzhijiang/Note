package com.spring.mvc.core.controller;

import com.spring.mvc.core.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 解决乱码演示的Controller
@Controller
public class SpringMvcBadCodeController {

    public static final String SUCCESS="success_bad_code";

    /*
     * ★测试入参为POJO
     * Spring MVC会按请求参数名和 POJO属性名进行自动匹配，
     * 		    自动为该对象填充属性值。支持级联属性
     */
    @RequestMapping("/testPOJO")
    // jsp中,form表单项的name属性值要与POJO类中的属性名保持一致,springmvc才可以将form表单中的数据封装到这个Employee中.
    public String testPOJO(Employee employee) {
        System.out.println("员工的信息是："+employee);
        return SUCCESS;
    }
}
