package com.filter.core.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 注意：从Tomcat8开始，默认编码已经改为UTF-8，所以已经不会出现Get请求乱码问题了。只需处理Post乱码。
 *
 * 这里的代码是有问题的!!
 */
@WebFilter(urlPatterns = "/test-encoding-servlet")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        System.out.println("CharacterEncodingFilter.......");

        // 【响应编码设置】后面Servlet拿到的Response对象都是已经设置过编码的
        res.setContentType("text/html;charset=utf-8");

        /*
         * 【请求编码设置】思路：
         * 1.特别注意，这里的request/response类型是ServletRequest/ServletResponse，我们要强转成Http相关的
         * 2.Filter传给后面Servlet的Request对象肯定不能是原先的，不然request.getParameter()还是会乱码
         *   这里使用动态代理生成代理对象，所以后面Servlet拿到的其实是代理Request
         * 3.对于Get、Post请求，解决乱码的方式是不同的，所以代理对象内部必须有针对两者的判断
         * */

        // 1.强转req/res 【思考一下为什么要加final】
        final HttpServletRequest request = (HttpServletRequest) req;

        // 2.使用Proxy.newProxyInstance()创建Request代理对象
        Object proxyRequest = Proxy.newProxyInstance(
                this.getClass().getClassLoader(), /*类加载器*/
                req.getClass().getInterfaces(), /*代理对象要和目标对象实现相同接口*/
                new InvocationHandler() { /*InvocationHandler，采用匿名内部类的方式*/
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("---------method.getName(): " + method.getName());
                        // 由于乱码的根源在于getParameter()，所以我们只盯着这个方法
                        if (!"getParameter".equalsIgnoreCase(method.getName())) {
                            return method.invoke(request, args);
                        }
                        // 3.判断是Get还是Post
                        if ("GET".equalsIgnoreCase(request.getMethod())) {
                            // 按默认编码ISO-8859-1取出
                            String value = (String) method.invoke(request, args);
                            // 按IOS-8859-1得到字节，再按UTF-8转成中文
                            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                            // 返回正确的中文
                            return value;
                        } else {
                            // 目标方法前设置编码
                            request.setCharacterEncoding("UTF-8");
                            Object value = method.invoke(request, args);
                            return value;
                        }
                    }
                });
        //把代理request对象传给Servlet
        chain.doFilter((HttpServletRequest) proxyRequest, res);
    }

    @Override
    public void destroy() {}
}
