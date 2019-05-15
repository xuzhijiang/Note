package org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.AbstractFactory;

import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.CacheRepository;
import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.Repository;

public class CacheRepositoryFactory implements RepositoryFactoryProvider{
    @Override
    public Repository create() {
        return new CacheRepository();
    }
}
