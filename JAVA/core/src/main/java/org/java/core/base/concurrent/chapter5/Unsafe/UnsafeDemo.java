package org.java.core.base.concurrent.chapter5.Unsafe;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeDemo {

    private int i = 0;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 实例化sun.misc.Unsafe:

        // Unsafe类提供了一个静态方法getUnsafe()来获取Unsafe的实例，
        // 但是如果你尝试调用Unsafe.getUnsafe()，会得到一个SecutiryException。这个类只有被JDK信任的类实例化。
        // Unsafe unsafe = Unsafe.getUnsafe();
        // System.out.println(unsafe);

        // 但是这总会是有变通的解决办法的，一个简单的方式就是使用反射进行实例化：

        //获取Unsafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); // Internal reference
        f.setAccessible(true);
        // 由于Unsafe是Unsafe类的静态变量,所以参数为null,如果是一个实例变量的话,要传对象的.也就是要获取这个对象中的这个field
        Unsafe unsafe = (Unsafe) f.get(null);

        //获取字段i在内存中偏移量
        Field i_field = UnsafeDemo.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(i_field);
        System.out.println("字段i在内存中偏移量: " + offset);

        //创建对象实例，设置i字段的值
        UnsafeDemo unsafeDemo = new UnsafeDemo();

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
}