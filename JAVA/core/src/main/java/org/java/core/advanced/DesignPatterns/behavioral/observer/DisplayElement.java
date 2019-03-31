package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 布告板显示接口
 */
public interface DisplayElement {
    //当布告板需要显示的，就会调用此方法
    public void display();
}