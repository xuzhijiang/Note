package com.funtl.hello.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 创建服务过滤器
 *
 * Zuul 的服务过滤演示
 *
 * 测试过滤器
 *
 * 浏览器访问：http://localhost:8769/api/a/hi?message=HelloZuul 网页显示
 * Token is empty
 *
 *
 * 浏览器访问：http://localhost:8769/api/b/hi?message=HelloZuul&token=123 网页显示
 * Hi，your message is :"HelloZuul" i am from port：8763
 */
@Component//继承 ZuulFilter 类并在类上增加 @Component 注解就可以使用服务过滤功能了，非常简单方便
public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * 1. pre：路由之前
     * 2. routing：路由之时
     * 3. post：路由之后
     * 4. error：发送错误调用
     * @return 返回一个字符串代表过滤器的类型，在 Zuul 中定义了四种不同生命周期的过滤器类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要过滤：true/需要，false/不需要
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体业务代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        logger.info("{} >>> {}", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");
        if (token == null) {
            logger.warn("Token is empty");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().write("Token is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("OK");
        }
        return null;
    }
}
