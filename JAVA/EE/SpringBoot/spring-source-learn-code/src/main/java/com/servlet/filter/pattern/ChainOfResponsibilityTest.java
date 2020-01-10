package com.servlet.filter.pattern;

import java.util.ArrayList;

/**
 * 责任链设计模式
 */
public class ChainOfResponsibilityTest {

    public static void main(String[] args) {
        Request request = new Request();
        Response response = new Response();

        Filter authenticationFilter = new AuthenticationFilter();
        Filter httpHeaderPrintFilter = new HttpHeaderPrintFilter();

        // 过滤器链
        FilterChain filterChain = new FilterChain();
        // 注册过滤器,链式调用
        filterChain.addFilter(authenticationFilter).addFilter(httpHeaderPrintFilter);
        // 开始执行过滤器
        filterChain.doFilter(request, response);
    }
}

class Request {

}

class Response {

}

interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);
}

// 认证过滤器
class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("验证用户是否是合法用户");
        // chain.doFilter(request, response);
    }
}

// 打印http header的过滤器
class HttpHeaderPrintFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("打印http header");
        chain.doFilter(request, response);
    }
}

class FilterChain {

    private ArrayList<Filter> filterList;

    private int index;

    public FilterChain() {
        filterList = new ArrayList<>();
    }

    public FilterChain addFilter(Filter filter) {
        filterList.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (index == filterList.size()) {
            return;
        }

        Filter filter = filterList.get(index);
        index++;

        filter.doFilter(request, response, this);
    }
}