package com.springboot.customAop.factory;

import com.springboot.customAop.manager.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂: 用于生成代理对象
 *
 * 代理对象方法 = 事务 + 目标对象方法。
 */
public class ProxyBeanFactory {

    // 通知
    private TransactionManager txManager;
    // 目标对象
    private Object target;

    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    // 传入目标对象target,为他装配好通知,然后返回代理对象
    public Object getProxy() {
        if (target != null) {
            Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), /*1.类加载器*/
                    target.getClass().getInterfaces(), /*2.目标对象实现的接口*/
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) {
                                Object result = null;
                                try {
                                    //1.开启事务
                                    txManager.beginTransaction();
                                    //2.执行操作
                                    result = method.invoke(target, args);
                                    //3.提交事务
                                    txManager.commit();
                                } catch (IllegalAccessException e) {
                                    //5.回滚事务
                                    txManager.rollback();
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    //5.回滚事务
                                    txManager.rollback();
                                    e.printStackTrace();
                                } finally {
                                    //6.释放连接
                                    txManager.release();
                                }
                                //4.返回结果
                                return result;
                            }
                        });
            return proxy;
        }
        return target;
    }
}
