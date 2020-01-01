package org.java.core.advanced.DesignPatterns.creational.abstractFactory;

// 具体工厂(具体的车工厂: 奔驰车工厂)
public class BenzCarFactory implements AbstractCarFactory {
    // 生产具体的车,这里就是奔驰
    @Override
    public Car getCar() {
        return new BenzCar();
    }
}
