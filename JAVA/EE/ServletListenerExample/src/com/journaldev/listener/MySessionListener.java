package com.journaldev.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// 创建或销毁会话时记录(log)事件的简单实现。
@WebListener
public class MySessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Created:: ID="+sessionEvent.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	System.out.println("Session Destroyed:: ID="+sessionEvent.getSession().getId());
    }
	
}
