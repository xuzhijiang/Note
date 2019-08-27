package org.java.core.base.collection.core;

import java.util.*;

public class TestCollection {

    public static void main(String[] args) {
        Collection<Person> collection = new ArrayList<Person>();
        Person p1 = new Person("aa", 20);
        collection.add(p1);
        System.out.println(collection.contains(p1));
        // 如果Person没有重写equals方法，那么就会返回false，
        // 这是因为两个对象的引用不一样，如果想两个对象的值一样就返回true的话，那么Person类就重写equals方法
        System.out.println(collection.contains(new Person("aa", 20)));

        System.out.println(Arrays.toString(collection.toArray()));

        // 遍历集合的方式:
        // 第一种:实现类内部是数组的实现方式.
        List<Person> myList = new ArrayList<>();
        myList.add(new Person("AA", 12));
        myList.add(new Person("CC", 14));
        myList.add(new Person("BB", 13));
        for(int i = 0 ; i <myList.size(); i++){
            // 注意：Collection没有get方法，意味着不能使用这种方法遍历
            System.out.println(myList.get(i));
        }
        // 第二种: JDK 1.5引入的foreach,
        // 注意如果是自定义容器,如果想要使用增强for循环,则需要实现Iterable接口.否则JDK不能识别
        for(Person p : collection){
            System.out.println(p);
        }
        // 第三种:iterator迭代器遍历
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    private static class Person {

        String name;
        int age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public Person(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }
        public Person() {
            super();
        }

        @Override
        public boolean equals(Object obj) {
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
            return true;
        }
    }

}
