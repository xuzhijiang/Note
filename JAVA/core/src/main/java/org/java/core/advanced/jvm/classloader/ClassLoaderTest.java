package org.java.core.advanced.jvm.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ClassLoaderTest {

    /**
     * 我们来分析下,HelloWorld类的加载过程,
     * 初始化自定义类加载器MyCustomClassLoader后,loadClass方法肯定将其委派到双亲Application ClassLoader,
     * 而Application ClassLoader又将其委派到Extension ClassLoader,继而委派到Bootstrap ClassLoader.
     * 但是Bootstrap ClassLoader发现Fib并不在自己的加载能力范围内,
     * 于是移向Extension ClassLoader,同理Extension ClassLoader只能加载/ext中的class,
     * 继而让给Application ClassLoader,而Application ClassLoader只加载classpath中的类,
     * 于是又回到我们自定义的MyClassLoader,幸好我们重写了findClass方法进而执行了加载
     * ,否在findClass抛出找不到类的异常.至此HelloWorld类加载完成.
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 初始化类加载器
        MyCustomClassLoader myCustomClassLoader = new MyCustomClassLoader("d:");
        // 加载HelloWorld类，笔者的HelloWorld类的包名为com.java.core, 注意name必须填全限定名,比如"java.lang.Object"
        Class helloWorldClass = myCustomClassLoader.loadClass("com.java.core.HelloWorld");
        // 获取加载的类的实例
        Object obj = helloWorldClass.newInstance();
        // 获取该类的一个名为print的方法
        Method print = helloWorldClass.getDeclaredMethod("print");
        print.setAccessible(true);
        // 执行print方法
        Object result = print.invoke(obj);
        // 打印执行结果
        System.out.println(result);
    }
}

// 自己动手,编写一个自己的类加载器
// 继承ClassLoader,重写findClass方法
class MyCustomClassLoader extends ClassLoader{

    // 保存的路径
    private String rootPath;

    /**
     * 传入自定义加载器的路径
     * @param rootPath 自定义MyCustomClassLoader类加载器的跟路径,类似于应用类加载器的classpath
     */
    public MyCustomClassLoader(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 重写findClass方法，让加载的时候调用findClass方法
     *
     * @param name findClass方法才是加载类到内存的,注意name必须填全限定名,比如"java.lang.Object"
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadByteCode(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            // 将字节码载入内存
            return defineClass(name, classData, 0, classData.length);
        }
    }

    /**
     * loadByte方法仅用作读取class文件
     */
    private byte[] loadByteCode(String className) {
        String path = classNameToPath(className);
        try {
            FileInputStream fis = new FileInputStream(path);
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把className转为对应操作系统的绝对路径
     * @param className
     */
    private String classNameToPath(String className) {
        return rootPath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
    }

}
