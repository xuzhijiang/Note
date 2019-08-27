package org.java.core.base.collection.queue.PriorityQueue;

public class Task {

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
