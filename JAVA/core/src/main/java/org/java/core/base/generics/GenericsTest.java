package org.java.core.base.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(10);
        list.add("hello");

        // 使用泛型声明集合，就只能向集合添加相同类型的数据
        // 集合中使用泛型可以达到类型安全，读取出来的对象不需要强转
        List<String> list2 = new ArrayList<String>();
        list2.add("world");
    }
}
