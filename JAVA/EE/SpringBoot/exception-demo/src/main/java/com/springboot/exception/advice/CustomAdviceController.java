package com.springboot.exception.advice;

import com.springboot.exception.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用ControllerAdvice定义一个类，在里面针对特定的异常进行处理
 */
// 限定被标注了@Controller或者@RestController的类才被拦截
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class CustomAdviceController {

    @ExceptionHandler(value = UserNotFoundException.class)// 要捕获哪一个异常
    @ResponseBody// 以JSON表达方式响应
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 定义返回的错误状态码
    public Map<String, Object> exception (HttpServletRequest request, UserNotFoundException ex) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("code", ex.getId());
        msgMap.put("message", ex.getMessage());
        msgMap.put("timestamp：", new Date().toString());
        return msgMap;
    }

}