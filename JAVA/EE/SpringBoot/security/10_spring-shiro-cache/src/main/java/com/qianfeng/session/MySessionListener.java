package com.qianfeng.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * @ClassName MySessionListener
 * @Description: TODO
 * @Author 臧红久
 * @Date 2019/10/10
 * @Version V1.0
 **/
public class MySessionListener extends SessionListenerAdapter{
    /**
     * session创建时触发
     * @param session
     */
    @Override
    public void onStart(Session session) {
        System.out.println("session listener create~~~");
    }

    /**
     * session 停止时触发    subject.logout()  session.stop()
     * @param session
     */
    @Override
    public void onStop(Session session) {
        System.out.println("session listener stop~~~");
    }

    /**
     * session过期时触发，静默时间查过了过期时间
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        System.out.println("session listener expire~~~");
    }
}
