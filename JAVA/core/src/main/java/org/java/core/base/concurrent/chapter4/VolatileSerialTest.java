package org.java.core.base.concurrent.chapter4;

import java.util.HashMap;
import java.util.HashSet;

public class VolatileSerialTest {

    static int x = 0, y = 0;
    // volatile static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        HashSet<String> resultSet = new HashSet<>();
        HashMap<String, Integer> resultMap = new HashMap<>();

        // 为了让各种结果打印出来,所以执行了100万次
        for (int i=0;i<1000000;i++) {
            x=0; y=0;
            resultMap.clear();
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    int a = y; // 指令重排后第3步
                    x = 1; // 指令重排后第一步
                    resultMap.put("a", a);
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    int b = x;// 指令重排后第4步
                    y = 1; // 指令重排后第2步
                    resultMap.put("b", b);
                }
            });

            one.start();
            other.start();
            one.join();
            other.join();

            // 如果不加volatile, a和b的情况有4中 a=0,b=0/a=0,b=1/a=1,b=0/a=1,b=1
            // 其中最后一种a和b都等于1是比较诡异的,我们程序中代码的运行顺序,
            // 这个就是由于jvm会对程序进行优化,也就是jvm会进行指令重排,\
            // 按照上面指令重排的顺序后,就会出现a和b都等于1
            // 我们可以给共享变量x和y加上volatile之后,就可以避免对x和y所在的语句进行指令重排
            // 因为加上volatile之后,就会在共享变量x和y的赋值前后加上内存屏障
            // volatile指令在汇编后会有lock指令前缀,这个lock指令前缀本身就具有内存屏障的功能
            // 也就是x=1这行代码一旦编译完成后,一旦cpu发现这行代码有内存屏障,那么int a = y;就不可能排到x=1这行代码后面去.
            // 也就是 cpu不会对这两行代码做交换来优化的.
            resultSet.add("a=" + resultMap.get("a") + "," + "b=" + resultMap.get("b"));
            System.out.println(resultSet);
        }
    }

}
