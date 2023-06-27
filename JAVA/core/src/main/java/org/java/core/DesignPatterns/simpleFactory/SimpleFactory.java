package org.java.core.DesignPatterns.simpleFactory;

// 简单工厂
public class SimpleFactory {
    /**
     * 为了减少代码的修改，设计原则是把变化的部分单独抽离出来，减少代码的改动
     * 这里抽离出来的就是车的名字
     */
    public static Car getCar(String name) {
        Car car = null;
        if(name.equals("Benz")) {
            car = new BenzCar();
        } else if (name.equals("LandRover")) {
            car = new LandRoverCar();
        }
        return car;
    }

}
