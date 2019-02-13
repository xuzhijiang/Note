package com.didispace.exception;

import com.didispace.dto.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

// 创建全局异常处理类：通过使用@ControllerAdvice定义统一的异常处理类，
// 而不是在每个Controller中逐个定义。
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler用来定义函数针对的异常类型，最后将Exception对象和请求URL映射到error.html中
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    /**
     * 返回JSON格式
     * 在上述例子中，通过@ControllerAdvice统一定义不同Exception映射到不同错误处理页面。
     * 而当我们要实现RESTful API时，返回的错误是JSON格式的数据，而不是HTML页面，
     * 这时候我们也能轻松支持。
     *
     * 本质上，只需在@ExceptionHandler之后加入@ResponseBody，
     * 就能让处理函数return的内容转换为JSON格式。
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }

}

