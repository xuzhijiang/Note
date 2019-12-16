package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User expected = new User(29, "zhangsan");
        AtomicReference<User> atomicReference = new AtomicReference<>(expected);
        System.out.println("user: " + atomicReference.get().toString());

        User update = new User(30, "lisi");
        System.out.println(atomicReference.compareAndSet(expected, update) + "\t " + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(expected, update) + "\t " + atomicReference.get().toString());
    }

    /**
     * 利用AtomicReference类把多个共享变量合并成一个共享变量来操作
     */
    private static class User {
        private int age; // 共享变量1
        private int v2; // 共享变量2
        private String name; // 共享变量2

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
