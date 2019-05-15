package org.java.core.advanced.DesignPatterns.behavioral.observer.demo;

import java.util.Observable;
import java.util.Observer;

public class AdminB implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("adminB: " + arg);
    }
}