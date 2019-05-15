package org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo;

import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.Repository;

public class FileRepository implements Repository {
    @Override
    public void save(Object obj) {
        System.out.println("save in File");
    }
}
