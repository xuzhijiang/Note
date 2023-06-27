package org.java.core.DesignPatterns.abstractFactory;

// 具体工厂(路虎车工厂)
public class LandRoverCarFactory implements AbstractCarFactory {
    @Override
    public Car getCar() {
        return new LandRoverCar();
    }
}
