package org.java.core.base.concurrent.chapter5.Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeInstance {

    public static Unsafe reflectGetUnsafe() {
        // Unsafe类有一个静态方法getUnsafe()来获取Unsafe的实例，
        // 但是如果你尝试调用Unsafe.getUnsafe()，会得到一个SecutiryException。这个类只有被JDK信任的类实例化。
        // Unsafe unsafe = Unsafe.getUnsafe();
        // System.out.println(unsafe);

        // 抛异常的具体原因:
        // 类加载是基于双亲委派模式的的,也就是父类加载器先加载.
        // bootstrap类加载器是最顶级的类加载器.Unsafe里面会判断如果不是bootstrap类加载加,
        // 直接抛出异常,只有通过bootstrap类加载器,才能拿到实例.

        // 所以Unsafe只能通过反射去拿.
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            // 由于Unsafe是Unsafe类的静态变量,所以参数为null,
            // 如果是一个实例变量的话,要传对象的.也就是要获取这个对象中的这个field
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
