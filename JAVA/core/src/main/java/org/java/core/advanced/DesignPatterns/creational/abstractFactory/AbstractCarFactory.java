package org.java.core.advanced.DesignPatterns.creational.abstractFactory;

// 抽象工厂(抽象的车工厂,具体是什么车工厂,不清楚)
// 比如奔驰车工厂和路虎车工厂都有共同的方法,就是生产一台车,所以把这两个车工厂 做了进一步抽象,变成抽象车工厂
public interface AbstractCarFactory {
    // 抽象工厂方法(生成一台车,具体是什么车,不清楚)
    Car getCar();
}
