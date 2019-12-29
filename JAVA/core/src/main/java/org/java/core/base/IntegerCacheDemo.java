package org.java.core.base;

import org.junit.Test;

/**
 * 8种基本类型的包装类和常量池
 *
 * 类似于String和String常量池,基本类型的包装类的大部分都实现了常量池技术，即Byte,Short,Integer,Long,Character；
 * 这5种包装类默认创建了[-128，127]的缓存数据，超出此范围仍然会去创建新的对象
 *
 * Float,Double的包装类 没有实现常量池技术
 */
public class IntegerCacheDemo {

    /*
        public static Integer valueOf(int i) {
            // -128到127之间的值会被缓存起来
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
        }
     */

    public static void main(String[] args) {
        // 在java编译器33是一个已经确定了的常量,所以Integer类一旦被加载,33这个对象自动会被创建,放到Integer的常量池中
        // 也就是会自动调用Integer.valueOf(i),将33缓存起来,从而使用常量池中的对象.
        // 执行Integer i1 = 33,相当于执行Integer i1 = Integer.valueOf(33);
        Integer i1 = 33; // 执行这行的时候,Integer的实例数量没有发生变化,因为在Integer被加载的时候就已经创建好了.可以加一个断点查看java.lang.Integer实例的数量.
        Integer i2 = 33; // 执行这行的时候,Integer的实例数量依然没有发生变化
        System.out.println(i1 == i2);// 输出true
        Integer i3 = new Integer(33);
        System.out.println(i1 == i3); // false, 会创建新的对象


        Integer i11 = 333;
        Integer i22 = 333;
        System.out.println(i11 == i22);// 输出false


        Double d3 = 1.2;
        Double d4 = 1.2;
        System.out.println(d3 == d4);// 输出false
    }

    @Test
    public void question() {
        Integer i1 = 40;
        Integer i2 = 40;
        Integer i3 = 0;
        Integer i4 = new Integer(40);
        Integer i5 = new Integer(40);
        Integer i6 = new Integer(0);

        System.out.println("i1=i2   " + (i1 == i2));
        System.out.println("i1=i2+i3   " + (i1 == i2 + i3));
        System.out.println("i1=i4   " + (i1 == i4));
        System.out.println("i4=i5   " + (i4 == i5));
        System.out.println("i4=i5+i6   " + (i4 == i5 + i6));
        System.out.println("40=i5+i6   " + (40 == i5 + i6));

        /*
            i1=i2   true
            i1=i2+i3   true
            i1=i4   false
            i4=i5   false
            i4=i5+i6   true
            40=i5+i6   true
         */

        // 语句i4 == i5 + i6，因为+这个操作符不适用于Integer对象，首先i5和i6进行自动拆箱操作，
        // 进行数值相加，即i4 == 40。然后Integer对象无法与数值进行直接比较，所以i4自动拆箱转为int值40，
        // 最终这条语句转为40 == 40进行数值比较。
    }
}
