package com.jinxuliang.datavalidator_demo.controller;

import com.jinxuliang.datavalidator_demo.domain.ValidatorPojo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 添加测试用的控制器

// 给示例项目添加一个控制器用于展示Spring MVC数据校验的功能。
@Controller
@RequestMapping("/datavalidate")
public class DataValidatorController {

    // 测试Get方法: localhost:8080/datavalidate/get,
    //向外界返回一个有效的数据实使用对象，以方便测试
    @GetMapping("/get")
    @ResponseBody
    public ValidatorPojo getValidatorPojo(){
        long now=new Date().getTime();
        // 从结果可以看到，在MVC控制器方法中直接返回一个对
        //象，客户端收到的其实是一个Json字符串。
        return new ValidatorPojo(100l, new Date((int)now+1000),
                500d,99, 888l,
                "jinxuliang@bit.edu.cn",
                "01234567890 01234567890 012345"
        );
    }



    // 在控制器中编写集成了校验功能的方法

//    示例中使用POST方式将Json数据放到HTTP请求的Body中传给控制器的相应方法，这
//    些数据会被实例化为方法的相应参数（@RequestBody起的作用）

//    给需要校验的方法参数加上@Valid注解，Spring Boot MVC就会提取信息创建参数
//    对象时对其进行数据校验，如果通不过，相关信息会放入到Errors集合中。

    /***
     * 解析验证参数错误
     * @param vp —— 需要验证的POJO，使用注解@Valid 表示验证
     * @param errors  错误信息，它由Spring MVC通过验证POJO后自动填充
     * @return 错误信息Map
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validate(
            @Valid @RequestBody ValidatorPojo vp, Errors errors) {
        Map<String, Object> infoMap = new HashMap<>();
        // 获取错误列表
        List<ObjectError> oes = errors.getAllErrors();
        if(oes.size()==0){
            infoMap.put("info",vp.toString());
        }
        else{
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

