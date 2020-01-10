package org.java.core.base.questions;

/**
 * 一句话总结：
 *
 * 对象类型永远传引用
 * 基本类型传值
 */
public class TransferValueOrReferenceDemo {

    public void changeValue1(int age) {
        age = 30;
    }

    public void changeValue2(Person person) {
        person.setPersonName("XXX");
    }

    public void changeValue3(String str) {
        str += "xxxxx";
    }

    public void changeValue4(Integer value) {
        value += 1;
    }

    public static void main(String[] args) {
        TransferValueOrReferenceDemo obj = new TransferValueOrReferenceDemo();
        int age = 20;
        obj.changeValue1(20);
        System.out.println("age-----" + age);

        Person person = new Person("abc");
        obj.changeValue2(person);
        System.out.println("personName------" + person.getPersonName());

        // 考点: String、包装类等对象的不可变性
        String str = "abc";
        obj.changeValue3(str);
        System.out.println("String------" + str);

        System.out.println("*************");

        Integer i = 200;
        obj.changeValue4(i);
        System.out.println("i: " + i);
    }

    private static class Person
    {
        String name;

        public  Person(String name) {
            this.name = name;
        }

        public void setPersonName(String name) {
            this.name = name;
        }

        public String getPersonName() {
            return name;
        }
    }
}
