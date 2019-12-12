package org.java.core.advanced.jvm.Throwable;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 注意: 要区分堆外内存和元空间所占用的堆外内存的差别,
 * 堆外内存是一个超集,元空间所占用的堆外内存是一个子集,堆外内存除了分配了一块空间给了元空间
 * ,还可以供ByteBuffer分配对象实现0拷贝
 *
 * Caused by: java.lang.OutOfMemoryError: Metaspace
 */
public class OOMMetaspaceDemo {

    static class OOMTest {}

    // VM Options: -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
    public static void main(String[] args) {
        int i = 0; // 模拟计数多少次之后发生异常

        try {
            // 使用cglib不停的创建新类(用spring的cglib的动态字节码技术不停的创建类),
            // 这些新类会被加载到元空间,把metaspace撑爆了
            // 最终会抛出: java.lang.OutOfMemoryError: Metaspace
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();

                //  设置生成代理类的父类class对象
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);

                // 设置增强目标类(Target)的方法拦截器
                // 创建目标类的方法增强拦截器,在拦截器内部，调用目标方法前后进行前置和后置增强处理
                MethodInterceptor methodInterceptor = new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        System.out.println("方法拦截增强逻辑-前置处理执行");
                        Object result = methodProxy.invoke(o, objects);
                        System.out.println("方法拦截增强逻辑-后置处理执行");
                        return result;
                    }
                };
                enhancer.setCallback(methodInterceptor);

                // 生成代理类
                enhancer.create();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("多少次之后发生异常************** i: " + i);
        }

    }

}
