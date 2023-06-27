package org.java.core.DesignPatterns.abstractFactory;

public class Main {
    public static void main(String[] args) {
        // 生产车,首先要确定生产什么车,所以先拿到这个具体车的工厂
        AbstractCarFactory factory = new BenzCarFactory();
        // 用具体的车工厂生产具体的车
        factory.getCar().drive();

        factory = new LandRoverCarFactory();
        factory.getCar().drive();
    }
}
