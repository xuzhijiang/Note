package com.jinxuliang.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// access: http:localhost:8080/asyncDispatch
// 示例展示了一个支持异步执行的Servlet，它休眠3秒之后，
// 然后再调用一个jsp显示其内容。
// 
// 注意：当有一个Servlet启用了异步特性，
// 则项目中的其他filter也应该启用异步特性，否则， tomcat将会抛出异常
@WebServlet(name = "AsyncDispatchServlet",
        urlPatterns = { "/asyncDispatch" },
        asyncSupported = true)
public class AsyncDispatchServlet extends HttpServlet {
    @Override
    public void doGet(final HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync();
        request.setAttribute("mainThread",
                Thread.currentThread().getName());
        asyncContext.setTimeout(5000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                // long-running task
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                request.setAttribute("workerThread",Thread.currentThread().getName());
                asyncContext.dispatch("/threadNames.jsp");
            }
        });
    }
}
