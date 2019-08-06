package com.springboot.mvc.controller.validate;

import com.springboot.mvc.domain.ValidatorPojo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
// 提交的数据有效时
POST: localhost:8080/datavalidate/post
{
	"id": 100,
	"date": "2019-12-19T03:28:45.449+0000",
	"doubleValue":500.0,
	"integer":34,
	"range":888,
	"email":"xuzhijing@bit.edu.cn",
	"size":"String length must be [20 30].",
	"phone": "12345678901"
}

// 提交非法数据
POST: localhost:8080/datavalidate/post
{
	"id": 100,
	"date": "2019-12-19T03:28:45.449+0000",
	"doubleValue":1500.0,
	"integer":34,
	"range":888,
	"email":"xuzhijing@bit.edu.",
	"size":"很短的字符串."
}
* */
@Controller
@RequestMapping("/datavalidate")
public class DataValidatorController {

    @GetMapping("/get")
    @ResponseBody
    public ValidatorPojo getValidatorPojo(){
        long now=new Date().getTime();
        return new ValidatorPojo(100l, new Date((int)now+1000),
                500d,99, 888l,
                "xzj@cn",
                "01234567890 01234567890 01256", "1234567890");
    }

    /***
     * 使用注解@Valid 表示vp需要校验,Spring Boot MVC就会提取请求体中的信息,在创建对象时对其进行数据校验，如果通不过，相关信息会放入到Errors集合中。
     * @param errors  错误信息，它由Spring MVC通过验证POJO后自动填充
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validate(@Valid @RequestBody ValidatorPojo vp, Errors errors) {
        Map<String, Object> infoMap = new HashMap<>();
        // 获取错误列表
        List<ObjectError> oes = errors.getAllErrors();
        System.out.println("size: " + oes.size());
        if (oes.size()==0) {
            infoMap.put("info",vp.toString());
        } else{
            for (ObjectError oe : oes) {
                String key = null;
                String msg = null;
                // 字段错误
                if (oe instanceof FieldError) {
                    FieldError fe = (FieldError) oe;
                    key = fe.getField();// 获取错误验证字段名
                } else {
                    // 非字段错误
                    key = oe.getObjectName();// 获取验证对象名称
                }
                // 错误信息
                msg = oe.getDefaultMessage();
                infoMap.put(key, msg);
            }
        }
        return infoMap;
    }
}