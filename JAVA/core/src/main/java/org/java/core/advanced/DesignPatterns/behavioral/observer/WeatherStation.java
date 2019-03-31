package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * WeatherData类中具有setter和getter方法，可以取得三个测量值，当有新的数据时，
 * WeatherData类中的measurementsChanged()方法就会被调用，我们要做的就是，一旦WeatherData类有新数据，
 * 我们就要更新布告板。
 */
public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay conditionsDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay observer2 = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        System.out.println("===================================");
        weatherData.setMeasurements(70, 55, 20.4f);
        System.out.println("===================================");
        weatherData.setMeasurements(90, 75, 40.4f);

    }

}