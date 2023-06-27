package org.java.core.DesignPatterns.simpleFactory;

public class Main {
    public static void main(String[] args) {
        // DriverManager.getConnection()
        Car car = SimpleFactory.getCar("Benz");
        car.drive();

        car = SimpleFactory.getCar("LandRover");
        car.drive();
    }
}
