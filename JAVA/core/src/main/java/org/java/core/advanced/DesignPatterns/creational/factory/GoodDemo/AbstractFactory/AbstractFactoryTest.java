package org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.AbstractFactory;

public class AbstractFactoryTest {

    public static void main(String[] args) {
        DatabaseRepositoryFactory databaseRepositoryFactory = new DatabaseRepositoryFactory();
        databaseRepositoryFactory.create().save(new Object());
        RepositoryFactoryProvider cacheProvider = new CacheRepositoryFactory();
        cacheProvider.create().save(new Object());
        RepositoryFactoryProvider fileProvider = new FileRepositoryFactory();
        fileProvider.create().save(new Object());
    }
}
