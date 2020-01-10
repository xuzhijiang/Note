package org.java.core.OOPDesignPrinciples.dip;

public class Student implements IPerson{
    @Override
    public void eat(IFood food) {
        System.out.println("学生要吃: " + food.getFoodName());
    }
}
