package org.java.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stream: 流式计算,计算流.
 *
 * 题目: 请按照给出的数据,找出同时满足以下条件的用户,也即以下条件全部满足:
 *
 *          偶数ID,且年龄大于25,且用户名转为大写,且用户名字母倒排序
 *          只输出一个用户名字
 */
public class StreamDemo {

    public static void main(String[] args) {
        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 25);
        User u4 = new User(14, "d", 26);
        User u5 = new User(15, "e", 27);
        User u6 = new User(16, "f", 28);

        // List和Array之间可以相互转换
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5, u6);

        // java8以及之后,List和Stream之间也可以相互转换.
        // list.stream(): 将List转换为Stream,这个Stream的泛型为List的泛型User
        list.stream().filter((t) -> {return t.getId() % 2 == 0;}).forEach(System.out::println);

        System.out.println("****************************");
        // filter返回的还是Stream,泛型依然是List的泛型
        list.stream().filter((t) -> {return t.getId() % 2 == 0;}).filter((t) -> {return t.getAge() > 24;}).forEach(System.out::println);

        System.out.println("****************************");
        // map返回类型依然为Stream,但是Stream的泛型类型变为了Function接口中apply的返回值类型
        // collect(Collectors.toList()): 把Stream转换为List,这个List的泛型和map中Function接口中apply的返回值类型一致.
        list.stream().map(t -> {return t.getAge() * 2;}).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("****************************");

        list.stream().filter((t) -> {
            return t.getId() % 2 == 0;
        }).filter((t) -> {
            return t.getAge() > 24;
        }).map(u -> {
            return u.getUserName().toUpperCase();
        }).sorted((o1, o2) -> {
            return o2.compareTo(o1);
        }).limit(1).forEach(System.out::println);

        System.out.println("****************************");
        // list.stream().count(): 等价于上面的这条sql:  select count(*) from book;
        // 流式计算: List中元素的数量
        System.out.println(list.stream().count());

        System.out.println("****************************");
        list.stream().map(t -> {return t.getAge() * 2;}).sorted().forEach(System.out::println);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    private static class User {
        private int id;
        private String userName;
        private int age;
    }
}
