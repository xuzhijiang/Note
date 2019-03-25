package org.java.core.base.collection.core.containsMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 关于这个contains方法需要仔细对待,contains依赖与对象的equals方法.
 * contains: 查看集合中是否包含某一个元素，返回一个布尔类型的值
 */
public class TestCollection {

    public static void main(String[] args) {
        Collection<Person> collection = new ArrayList<Person>();
        Person p1 = new Person("aa", 20);
        collection.add(p1);
        System.out.println(collection.contains(p1));//true

        // 如果Person没有重写equals方法，那么就会返回false，
        // 这是因为两个对象的引用不一样，如果想两个对象的值一样就返回true的话，那么Person类就重写equals方法
        System.out.println(collection.contains(new Person("aa", 20)));
        System.out.println("size01: " + collection.size());//size:1

        collection.add(new Person("AA", 12));
        collection.add(new Person("BB", 13));
        collection.add(new Person("CC", 14));

        Collection<Person> list = new ArrayList<Person>();
        list.add(new Person("AA", 12));
        list.add(new Person("BB", 13));
        System.out.println("size02: " + collection.size());//size:4

        boolean containsAll = collection.containsAll(list);
        System.out.println(containsAll);

        collection.retainAll(list);//将当前集合中与形参集合一样的元素保留
        System.out.println(collection);
        System.out.println("size03: " + collection.size());//size:2

        // 删除集合中某一个元素，删除成功返回true，否则false，这里返回false
        boolean remove = collection.remove(new Person("DD", 12));
        System.out.println(remove);

        // 判断当前集合是否与形参集合完全相同,这里返回true
        Collection<Person> c1 = new ArrayList<Person>();
        c1.add(new Person("dd", 12));
        c1.add(new Person("ee", 13));
        Collection<Person> c2 = new ArrayList<Person>();
        c2.add(new Person("dd", 12));
        c2.add(new Person("ee", 13));
        boolean equals = c1.equals(c2);
        System.out.println(equals);

        // 同样的代码，顺序不一样，返回的结果也就不一样，这里返回false，只是交换了集合中存储对象的顺序。
        // 这里返回false
        c1.clear();
        c1.add(new Person("dd", 12));
        c1.add(new Person("ee", 13));
        c2.clear();
        c2.add(new Person("ee", 13));
        c2.add(new Person("dd", 12));
        equals = c1.equals(c2);
        System.out.println(equals);

        Object[] arr = collection.toArray();
        for(int i = 0; i<arr.length; i++){
            System.out.println(arr[i]);
        }

        Iterator<Person> iterator = collection.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        // 遍历集合的三种方式:

        // 第一种:实现类内部是数组的实现方式.
        // 注意：Collection没有get方法，意味着不能使用这种方法遍历
        System.out.println("通过索引和get方法遍历");

        List<Person> myList = new ArrayList<Person>();
        myList.add(new Person("AA", 12));
        myList.add(new Person("CC", 14));
        myList.add(new Person("BB", 13));
        for(int i = 0 ; i <myList.size(); i++){
            Person person = myList.get(i);
            System.out.println(person);
        }

        // 第二种:增强for循环
        System.out.println("增强for遍历");
        for(Person p : collection){
            System.out.println(p);
        }

        // 第三种:iterator迭代器遍历
        System.out.println("使用迭代器遍历");
        iterator = collection.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
