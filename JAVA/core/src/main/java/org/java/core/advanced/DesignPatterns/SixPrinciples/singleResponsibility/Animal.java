package org.java.core.advanced.DesignPatterns.SixPrinciples.singleResponsibility;

/**
 * 动物呼吸的样子
 */
public class Animal {

    public void breathe(String animal) {
        System.out.println(animal + "呼吸空气");
    }

    // 多态方法,方法的 职责功能单一

    /**
     * 方法里面处理的功能职责单一,就不太容易出错.否则方法里面一堆if-else,时间长了,容易出错.
     * 一个方法里面有几百行代码,从头看到尾, 这样很容易出问题
     * @param animal
     */
    public void breatheWater(String animal) {
        System.out.println(animal + "吃水");
    }
}
