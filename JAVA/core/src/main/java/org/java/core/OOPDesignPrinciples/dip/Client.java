package org.java.core.OOPDesignPrinciples.dip;

public class Client {
    public static void main(String[] args) {
        IFood food1 = new Fish();
        IFood food2 = new Pig();

        IPerson person = new Student();
        person.eat(food1);
        person.eat(food2);
    }
}
