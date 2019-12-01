package com.springboot.mvc.controller;

import com.springboot.mvc.domain.Employee;
import com.springboot.mvc.validator.EmployeeFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class EmployeeValidateController {

    @Autowired
    private EmployeeFormValidator validator;

    // 我们需要一个将WebDataBinder作为参数的方法，
    // 并设置我们要使用的自定义验证器。 此方法应使用@InitBinder注释
    @InitBinder("employee")
    public void initBinder(WebDataBinder binder) {
        // 每个http请求,调用一次,Each time this method is called a new instance of WebDataBinder is passed to it.如果我们指定了InitBinder的值,就只会在匹配这个值的情况下被调用.
        // What we can do with WebDataBinder?
        // It can be used to register custom formatter, validators and PropertyEditors.
        System.out.println("init Binder called!");
        binder.addValidators(validator);
    }

    // Spring还提供了@Validator注释和BindingResult类，
    // 通过它我们可以在控制器请求处理程序方法中获取由Validator实现引发的错误。
    // @Valid和@Validated的区别,一个是javax相关注解的验证,一个是spring注解的验证,这里因为EmployeeFormValidator是使用Spring的注解定义的,所以....
    @RequestMapping(value = "/emp/save", method = RequestMethod.POST)
    @ResponseBody
    public Object saveEmployeeAction(@Validated @RequestBody Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors;
        }
        return employee;
    }

    //------------------@ModelAttribute的使用---------------------
    //@RequestMapping(value = "/emp/s", method = RequestMethod.POST)
    // 把提交上来的,表单中的employee所对应的对象内容填充到ee这个对象中
    // ModelAttribute value should be same as used in the jsp view.
    //public String modelAttrivute(@ModelAttribute("employee") Employee ee) {
    //    return ee.toString();
    //}
}
