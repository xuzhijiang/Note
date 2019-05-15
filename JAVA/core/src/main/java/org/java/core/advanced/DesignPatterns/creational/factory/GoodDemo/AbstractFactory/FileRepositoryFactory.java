package org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.AbstractFactory;

import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.FileRepository;
import org.java.core.advanced.DesignPatterns.creational.factory.GoodDemo.Repository;

public class FileRepositoryFactory implements RepositoryFactoryProvider{
    @Override
    public Repository create() {
        return new FileRepository();
    }
}
