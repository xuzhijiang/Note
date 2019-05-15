package org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.SimpleFactory;

import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.CacheRepository;
import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.DatabaseRepository;
import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.FileRepository;
import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.Repository;

public class SimpleRepositoryFactory {

    public Repository create(String type) {
        Repository repository = null;
        switch(type) {
            case "db":
                repository = new DatabaseRepository();
                break;
            case "cache":
                repository = new CacheRepository();
                break;
            case "file":
                repository = new FileRepository();
                break;
        }
        return repository;
    }

    public static void main(String[] args) {
        SimpleRepositoryFactory factory = new SimpleRepositoryFactory();
        factory.create("db").save(new Object());
        factory.create("cache").save(new Object());
        factory.create("file").save(new Object());
    }
}
