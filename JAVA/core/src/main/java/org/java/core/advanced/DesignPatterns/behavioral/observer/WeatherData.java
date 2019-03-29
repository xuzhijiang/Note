package org.java.core.advanced.DesignPatterns.behavioral.observer;

import java.util.ArrayList;

public class WeatherData implements Subject {
    private ArrayList<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    //使用一个集合来记录观察值
    public WeatherData() {
        observers = new ArrayList<Observer>();
    }
    //注册成为观察者
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);

    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i >=0){
            observers.remove(o);
        }
    }
    //这里就是将状态循环的通知给每一个观察者，因为每一个观察者都实现了update方法，
    @Override
    public void notifyObserver() {
        for(int i=0; i < observers.size();i++){
            Observer observer = observers.get(i);
            observer.update(temperature, humidity, pressure);
        }
    }
    //当气象站得到更新数据中，就通知观察者
    public void measurementsChanged(){
        notifyObserver();
    }
    public void setMeasurements(float temperature,float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();

    }
}