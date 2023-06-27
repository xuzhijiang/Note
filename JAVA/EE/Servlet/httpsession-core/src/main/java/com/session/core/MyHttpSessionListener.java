package com.session.core;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// 问题1: session在第一次使用的时候创建,什么叫第一次使用的时候创建呢?
// client直接访问html页面,session此时并不会被创建,因为html页面中没有使用到session.
// 当client第一次访问jsp的时候,因为这个client和server还没有建立会话,
// 因为jsp页面要使用隐含的session对象,此时session还没有创建好,所以就进行session的创建.
// 我们的jsp页面之所以可以使用session对象,是因为人家帮我们创建好了.


// 如果Servlet中调用了request.getSession()，则Tomcat会创建Session（如果根据JSESSIONID找不到对应的），这会被HttpSessionListener监听到
// 2. 用户30分钟未访问，Session过期销毁，被监听到
// 3. 只有当在Servlet中调用request.getSession()，且根据JSESSIONID找不到对于的Session时，才会创建新的Session对象，才会被监听到。第二次请求，浏览器会带上JSESSIONID，此时虽然还是request.getSession()，但是会返回上次那个。根据JSESSIONID去找Session这个过程是隐式的，我们看到的就是getSession()。这时不会出发session created事件.
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    // 创建HttpSession
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        //打印 Session的唯一ID
    	System.out.println("*********AAAAAAAAAA******session创建了***"+sessionEvent.getSession().getId());
    }

    // 销毁HttpSession
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	System.out.println("*********AAAAAAAAAA******session销毁了***"+sessionEvent.getSession().getId());
    }
}
