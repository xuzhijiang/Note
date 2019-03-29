package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 天气统计布告板：显示最高温度，最低温度和平均温度
 */
public class StatisticsDisplay implements Observer,DisplayElement{
    private float maxTemp = 0.0f;
    private float minTemp = 200;
    private float tempSum= 0.0f;
    private int numReadings;
    private WeatherData weatherData;

    public StatisticsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;

        if (temperature > maxTemp) {
            maxTemp = temperature;
        }

        if (temperature < minTemp) {
            minTemp = temperature;
        }

        display();
    }

    public void display() {
        System.out.println("我是天气统计布告板，平均温度是： " + (tempSum / numReadings)
                + "，最高温度是：" + maxTemp + "，最低温度是：" + minTemp);
    }

}