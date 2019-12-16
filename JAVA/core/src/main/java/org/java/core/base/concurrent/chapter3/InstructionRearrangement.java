package org.java.core.base.concurrent.chapter3;

import java.util.HashMap;
import java.util.Map;

public class InstructionRearrangement {

    // 这个只是伪代码,因为指令重排不是肯定会发生
    public static void main(String[] args) {
        ShardData shardData = new ShardData();

        new Thread(() -> {
            shardData.doSomething();
        }, "B").start();

        new Thread(() -> {
            shardData.initMap();
        }, "A").start();
    }

    /**
     * 指令重排的演示
     *
     * 这里就能看出问题了，当flag没有被 volatile修饰时，JVM对1和2进行重排，
     * 导致map 都还没有被初始化就有可能被线程 B 使用了。所以加flag上 volatile之后可以防止这样的重排优化，
     * 保证业务的正确性。不加volatile,多线程中可能会出现数据不一致的问题
     */
    private static class ShardData {
        private static Map<String,String> map;
        // map是否初始化成功的标记,map在线程A中进行初始化
        private static boolean flag = false;
//    private static volatile boolean flag = false;

        //以下方法发生在线程A 中 初始化 Map
        public void initMap(){
            //耗时操作
            map = getMapValue() ;// 1
            flag = true ; // 2
        }

        private Map<String, String> getMapValue() {
            Map<String, String> map = new HashMap<>();
            map.put("a", "A");
            return map;
        }

        // 这个方法发生在线程 B中 等到 Map 初始化成功进行其他操作
        public void doSomething(){
            // 如果map没有初始化完成就sleep
            while(!flag){
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 使用map做一些事情
            printMapInfo(map);
        }

        private void printMapInfo(Map<String, String> map) {
            System.out.println("map size: " + map.size());
        }
    }
}
