package org.java.core.advanced.DesignPatterns.structural.decorator.GoodDemo;

public class DecoratorTest {

    public static void main(String[] args) {
        SimpleQuery simpleQuery = new SimpleQuery();
        System.out.println(simpleQuery.query("select * from t_student") == simpleQuery.query("select * from t_student"));// false

        CacheQuery cacheQuery = new CacheQuery(simpleQuery);
        System.out.println(cacheQuery.query("select * from t_student") == cacheQuery.query("select * from t_student"));// true

        Query filterQuery = new FilterQuery(simpleQuery);
        System.out.println(filterQuery.query("select * from t_student where name = 'fuck'"));  // null
        System.out.println(filterQuery.query("select * from t_student where name = 'format'"));
    }
}
