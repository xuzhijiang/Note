package com.java.datastructure.collection.queue.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class PriorityQueueTest {

    public static void main(String[] args) {

        testIntegerPriorityQueue();

        testStringPriorityQueue();

        testCustomComparator();
    }

    private static void testStringPriorityQueue() {
        // 使用String的自然顺序进行排序(也就是String实现的Comparable接口的compareTo方法)
        // 这种是默认情况，元素是按升序排序，每次拿数据都是拿优先级最高的元素，所以越小的数，优先级越高，越早被拿到.
        // 队列头部是优先级最高的元素(数值最小)
        PriorityQueue<String> queue=new PriorityQueue<>();
        queue.add("task1");
        queue.add("task4");
        queue.add("task3");
        queue.add("task2");
        queue.add("task5");
        while(queue.size() > 0){
            System.out.println("traverse queue: " + queue.poll());
        }

        // 传递一个逆序排序的Comparator
        // 内部其实使用的是Collections内部定义的内部静态私有ReverseComparator类
        PriorityQueue<String> reverseQueue=new PriorityQueue<String>(Comparator.reverseOrder());
        reverseQueue.add("task1");
        reverseQueue.add("task4");
        reverseQueue.add("task3");
        reverseQueue.add("task2");
        reverseQueue.add("task5");

        while(reverseQueue.size() > 0){
            System.out.println("traverse reverseQueue: " + reverseQueue.poll());
        }

        System.out.println("queue comparator: " + queue.comparator());
        System.out.println("reverseQueue comparator: " + reverseQueue.comparator());
    }

    /**
     * 比较使用Integer实现的Comparable接口
     */
    private static void testIntegerPriorityQueue() {
        // 创建PriorityQueue的时候没有传Comparator的实现类
        Queue<Integer> queue = new PriorityQueue<>(7);
        Random rand = new Random();
        for(int i=0;i<7;i++){
            // 因为没有传Comparator的实现类，
            // 所以比较的的时候，使用的是Integer实现的接口Comparable<Integer>的compareTo方法
            queue.add(new Integer(rand.nextInt(100)));
        }
        for(int i=0;i<7;i++){
            Integer element = queue.poll();
            System.out.println("poll: " + element);
        }
        System.out.println("queue size: " + queue.size());
    }

    private static void testCustomComparator() {
        PriorityQueue<Task> queue = new PriorityQueue<Task>(6, new Comparator<Task>() {
            // 这个类放到PriorityQueue里，level越大优先级越高
            @Override
            public int compare(Task t1, Task t2) {
                return t2.getLevel() - t1.getLevel();
            }
        });

        queue.add(new Task("游戏", 20));
        queue.add(new Task("吃饭", 100));
        queue.add(new Task("睡觉", 90));
        queue.add(new Task("看书", 70));
        queue.add(new Task("工作", 80));
        queue.add(new Task("撩妹", 10));
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

}
