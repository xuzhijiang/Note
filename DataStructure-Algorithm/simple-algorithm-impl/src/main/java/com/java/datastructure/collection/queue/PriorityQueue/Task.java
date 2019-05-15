package com.java.datastructure.collection.queue.PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Task {

    public static void main(String[] args) {
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

    String name;
    int level;

    public Task(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }


}
