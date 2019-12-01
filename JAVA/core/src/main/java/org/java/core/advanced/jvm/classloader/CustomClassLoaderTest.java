package org.java.core.advanced.jvm.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 分析自定义类加载器的加载过程
 */
public class CustomClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 1. 初始化自定义类加载器MyCustomClassLoader
        MyCustomClassLoader myCustomClassLoader = new MyCustomClassLoader("d:");
        System.out.println(myCustomClassLoader.getParent());
        System.out.println(myCustomClassLoader.getParent().getParent());
        System.out.println(myCustomClassLoader.getParent().getParent().getParent());
        // 2. 通过loadClass方法加载com.java.core.HelloWorld类
        // 3. 根据双亲委派机制,将其委派到双亲"应用类加载器"
        // 4. "应用类加载器"将其委派到"扩展类加载器"
        // 5. "扩展类加载器"委派给"启动类加载器"
        // 6. "启动类加载器"不能够加载,返回了null,所以"扩展来加载器"会通过findClass()来自己加载
        // 7. "扩展来加载器"会抛出ClassNotFoundException(Ext加载器只能加载jre/lib/ext/*.jar),所以返回到"应用类加载器"
        // 8. "应用类加载器"捕获异常,然后通过findClass()来自己加载(只能加载用户类路径),也没有加载到,会抛出ClassNotFoundException
        // 9. MyCustomClassLoader捕获异常,然后通过findClass()来自己加载
        Class helloWorldClass = myCustomClassLoader.loadClass("com.java.core.HelloWorld");
        Object obj = helloWorldClass.newInstance();
        Method method = helloWorldClass.getDeclaredMethod("print");
        method.setAccessible(true);
        Object result = method.invoke(obj);
        System.out.println(result);
    }

    /**
     * 自定义类加载器
     * 1. 继承ClassLoader
     * 2. 重写findClass方法
     */
    private static class MyCustomClassLoader extends ClassLoader{

        private String rootPath;

        /**
         * @param rootPath 自定义类路径,自定义类加载器会从这个路径下加载类
         */
        public MyCustomClassLoader(String rootPath) {
            this.rootPath = rootPath;
        }

        /**
         * @param name 类的全限定名
         * @return
         * @throws ClassNotFoundException
         */
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData = loadByteCode(name);
            if (classData == null) {
                throw new ClassNotFoundException();
            } else {
                // 将字节码载入内存
                // //调用类加载器本身的defineClass()方法，由字节码得到Class对象
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

}


