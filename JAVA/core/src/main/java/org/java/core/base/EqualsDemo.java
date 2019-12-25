package org.java.core.base;

import java.util.Arrays;

/**
 * 1. ==两端如果是基本数据类型，则比较的是值是否相等
 * 2. ==两端如果是引用类型,则比较的是地址是否相等
 */
public class EqualsDemo {

    public static void main(java.lang.String[] args) {
        int i = 100; char c = 100;
        System.out.println(i==c);//true
        System.out.println("c: " + c);//d
        float f = 100.0F;
        System.out.println(i==f);//true
        int k = 65; char A = 'A';
        System.out.println("A: " + A);//A
        System.out.println(k==A);//true
        char a = 97;
        System.out.println("a: " + a);//a

        long aa = 30L;
        long b = 40L;
        System.out.println("aa == b " + (aa == b));

        float f2 = 100.303F;
        float f3 = 100.303f;
        System.out.println("f2: " + Float.floatToIntBits(f2));
        System.out.println("f3 == f2: " + (Float.floatToIntBits(f3) == Float.floatToIntBits(f2)));
        System.out.println("f3 == f2: " + (f2 == f3));

        double d1 = 200.35829823D;
        double d2 = 200.35829823d;
        System.out.println("d1 == d2 ? " + (d1 == d2));
        System.out.println("d1 == d2 ? " + (Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2)));

        // 数组的比较
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {1, 2, 3, 4};
        System.out.println("arr1 == arr2 : " + Arrays.equals(arr1, arr2));

        // 对象的比较
        Person p1 = new Person();
        Person p2 = new Person();
        p1.name = "name";
        p2.name = "name";
        p1.age = 30;
        p2.age = 30;
        p1.sex = "male";
        p2.sex = "male";
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));
    }

    static class Person {
        private String name;//姓名

        private String sex;//性别

        private int age;//年龄

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Person other = (Person) obj;
            if (age != other.age)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (sex == null) {
                if (other.sex != null)
                    return false;
            } else if (!sex.equals(other.sex))
                return false;
            return true;
        }
    }

}

