package org.java.core.base.collection.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类的多线程不安全
 *
 * ArrayList/HashSet/HashMap都是多线程不安全的
 */
public class ContainerNotUnsafeDemo {

    public static void main(String[] args) {
//        ArrayListUnsafe();

//        HashSetUnsafe();

//        HashMapUnsafe();
    }

    private static void HashMapUnsafe() {
//        Map<String, String> map = new HashMap<>(); // java.util.ConcurrentModificationException
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void HashSetUnsafe() {
//        Set<String> set = new HashSet<>(); // 也会出现java.util.ConcurrentModificationException
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    private static void ArrayListUnsafe() {
        //         List<String> list = new ArrayList<String>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        /**
         * 不要只是会用,否则只是一个api调用工程师
         *
         * 1. 故障现象:
         *          java.util.ConcurrentModificationException
         * 2. 导致原因:
         *          并发争抢修改导致,参考我们的花名册签名情况(只有一支笔,但是有很多人同时来签名)
         *          一个人正在写,另外一个同学过来抢夺,导致数据不一致异常,并发修改异常.
         *
         * 3. 解决方案:
         *  3.1 new Vector<>();这是一个线程安全的List,但是Vector很少用,很少用的原因: 因为它使用了synchronized,并发性急剧下降.
         *  3.2 Collections.synchronizedList(new ArrayList<>()); 内部也是使用了synchronized
         *  3.3 new CopyOnWriteArrayList<>(); 写时复制(是一种读写分离的思想)-最终我们使用的是这个List,是一个线程安全的List,
         *  内部使用的是ReentrantLock来保证线程安全.
         *
         *  面试官会考你,对于并发情况,各种List的熟练情况和掌握情况,基本上这三个你能答出来,
         *  说明你具备在并发下面干活的资质和基础知识扎实.
         *
         * 4. 优化建议(同样的错误不犯2次)
         *
          */
    }

}
