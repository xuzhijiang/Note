package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 天气预报布告板：显示当前的天气状况
 */
public class ForecastDisplay implements Observer,DisplayElement{
    private float currentPressure = 29.92f;  //当前的气压
    private float lastPressure; // 最后的气压
    private WeatherData weatherData;

    public ForecastDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void update(float temperature,float humidity,float pressure) {
        lastPressure = currentPressure;// 将当前的气压赋给最后的气压
        currentPressure = pressure;// 将更新的气压赋给当前气压
        display();
    }

    public void display() {

        if (currentPressure > lastPressure) {
            System.out.println("我是天气预报布告板，现在是：气压升高，天气转好！");
        } else if (currentPressure == lastPressure) {
            System.out.println("我是天气预报布告板，现在是：气压不变，天气维持！");
        } else if (currentPressure < lastPressure) {
            System.out.println("我是天气预报布告板，现在是：气压降低，天气变坏！");
        }
    }

}