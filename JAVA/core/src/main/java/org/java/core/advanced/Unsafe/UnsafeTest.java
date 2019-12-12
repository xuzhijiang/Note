package org.java.core.advanced.Unsafe;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeTest {

    private int i = 0;

    public static void test01() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

        Field i_field = UnsafeTest.class.getDeclaredField("i");
        // 获取字段i在内存中偏移量
        long offset = unsafe.objectFieldOffset(i_field);
        System.out.println("字段i在内存中偏移量: " + offset);

        //创建对象实例，设置i字段的值
        UnsafeTest unsafeDemo = new UnsafeTest();

        // 注意在这个案例中，我们不是直接给int型变量i赋值，而是通过调用以下方法进行赋值：

        // 其中offset是表示的是i在内存中的偏移量。何谓偏移量？
        // JVM的实现可以自由选择如何实现Java对象的“布局”，
        // 也就是在内存里Java对象的各个部分放在哪里，包括对象的实例字段和一些元数据之类。
        // sun.misc.Unsafe里关于对象字段访问的方法把对象布局抽象出来，它提供了objectFieldOffset()
        // 方法用于获取某个字段相对Java对象的“起始地址”的偏移量，也提供了getInt、getLong、
        // getObject之类的方法可以使用前面获取的偏移量来访问某个Java对象的某个字段。
        unsafe.putInt(unsafeDemo, offset, 100);

        System.out.println(unsafeDemo.i);
    }

    /**
     * 使用Unsafe创建类的实例
     */
    public static void createInstance() throws InstantiationException {
        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

        // 通过allocateInstance()方法，你可以创建一个类的实例，
        // 但是却不需要调用它的构造函数,即使构造函数是私有，这个实例里面的变量是没有初始化过的.
        Player p = (Player) unsafe.allocateInstance(Player.class);
        System.out.println(p.getAge()); // Print 0

        p.setAge(45); // Let's now set age 45 to un-initialized object
        System.out.println(p.getAge()); // Print 45
    }

    private static class Player {
        private int age = 12;

        // 注意是private
        private Player() {
            this.age = 50;
        }

        public int getAge() {
            return this.age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    /**
     * 获得对象在内存中的地址
     * @param o
     * @return
     * @throws Exception
     */
    public static long addressOf(Object o) throws Exception {
        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

        Object[] array = new Object[] {o};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }
}