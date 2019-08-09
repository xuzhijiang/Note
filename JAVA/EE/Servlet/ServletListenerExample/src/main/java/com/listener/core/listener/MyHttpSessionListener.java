package com.listener.core.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// 创建或销毁HttpSession时记录分配给Session的唯一ID
// 1. 如果Servlet中调用了request.getSession()，则Tomcat会创建Session（如果根据JSESSIONID找不到对应的），这会被HttpSessionListener监听到
// 2. 用户30分钟未访问，Session过期销毁，被监听到
// 3. 只有当在Servlet中调用request.getSession()，且根据JSESSIONID找不到对于的Session时，才会创建新的Session对象，才会被监听到。第二次请求，浏览器会带上JSESSIONID，此时虽然还是request.getSession()，但是会返回上次那个。根据JSESSIONID去找Session这个过程是隐式的，我们看到的就是getSession()。这时不会出发session created事件.
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Created:: ID="+sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Destroyed:: ID="+sessionEvent.getSession().getId());
    }
	
}
