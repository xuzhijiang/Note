package org.java.core.base.ObjectClass.EqualsOperator;

public class EqualsOperatorTest {

    public static void main(String[] args){

        testPrimitiveType();

        testReferenceType();

    }

    private static void testPrimitiveType() {
        // “==”两端如果比较的是基本数据类型，则基本数据类型的值是否相等，相等就返回true，否则返回false，
        // 重点比较的是基本数据类型的值，两端的数据类型可能不同
        int i = 100;
        int j = 100;
        char c = 100;
        float f = 100.0F;
        int k = 65;
        char A = 'A';
        char a = 97;
        System.out.println(i==j);//true
        System.out.println(i==c);//true
        System.out.println(i==f);//true
        System.out.println(k==A);//true
        System.out.println("c: " + c);//d
        System.out.println("A: " + A);//A
        System.out.println("a: " + a);//a
    }

    private static void testReferenceType() {
        // “==”两端比较的是引用类型，判断的是引用类型变量的地址值是否相等

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = obj1;
        System.out.println(obj1 == obj2);//false
        System.out.println(obj3 == obj1);//true

    }
}
