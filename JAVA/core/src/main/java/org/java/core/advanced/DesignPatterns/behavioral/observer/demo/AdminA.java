package org.java.core.advanced.DesignPatterns.behavioral.observer.demo;

import java.util.Observable;
import java.util.Observer;

// Observer
public class AdminA implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("adminA : " + arg);
    }
}
