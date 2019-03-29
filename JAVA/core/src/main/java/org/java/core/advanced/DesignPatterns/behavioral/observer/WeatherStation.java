package org.java.core.advanced.DesignPatterns.behavioral.observer;

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