package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 显示目前状况的布告板：显示温度，湿度和气压
 */
public class CurrentConditionsDisplay implements Observer,DisplayElement {

    private float temperature;// 温度
    private float humidity;// 湿度
    private float pressure; // 气压
    private Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    @Override
    public void display() {
        System.out.println("我是目前状况布告板，现在温度是："+temperature+
                "℃"+"湿度是："+humidity+"气压是："+pressure);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

}