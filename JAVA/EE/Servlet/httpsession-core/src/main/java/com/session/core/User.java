package com.session.core;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * 当session中保存了User对象,并且想要让User对象支持活化,钝化,那么就要让User实现序列化接口
 *
 * HttpSessionBindingListener和HttpSessionActivationListener不需要在web.xml中注册,
 * 其他的6个监听器需要注册在web.xml中.
 *
 * 所以要理解: 活化,钝化的监听只针对于User对象,而其他的6个监听器是针对所有 所有对象在HttpSession域中属性的变化.
 */
public class User implements Serializable, HttpSessionBindingListener, HttpSessionActivationListener {

    /**
     * session将要钝化
     */
    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("User[" + this + "] 将要和session一起钝化了, session: " + se.getSession());
    }

    // session活化
    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("User[" + this + "] 活化回来啦,session " + se.getSession());
    }

    // 绑定
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("User[" + this + "] 绑定,session " + event.getSession());
    }
    //解绑
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("User[" + this + "] 解绑, session " + event.getSession());
    }
}
