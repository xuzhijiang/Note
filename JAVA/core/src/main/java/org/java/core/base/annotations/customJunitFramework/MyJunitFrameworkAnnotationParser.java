package org.java.core.base.annotations.customJunitFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 自定义Junit框架解析器
 *
 * 用到注解的地方，必然存在三步: 定义注解,使用注解,解析注解
 */
public class MyJunitFrameworkAnnotationParser {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = JunitTestSimplePojo.class.getName();
        System.out.println("class name: " + className);
        doTest(className);
    }

    /**
     * @param className 测试类名
     */
    public static void doTest(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // 测试类的字节码
        Class<?> clazz = Class.forName(className);

        // 获取EmployeeDAOTest类中所有的公共方法
        Method[] methods = clazz.getMethods();

        // 迭代出每一个Method对象判断哪些方法上使用了@MyBefore/@MyAfter/@MyTest注解
        ArrayList<Method> beforeList = new ArrayList<>();
        ArrayList<Method> afterList = new ArrayList<>();
        ArrayList<Method> testList = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(MyBefore.class)) {
                beforeList.add(m);
            } else if (m.isAnnotationPresent(MyAfter.class)) {
                afterList.add(m);
            } else if (m.isAnnotationPresent(MyTest.class)) {
                testList.add(m);
            }
        }

        Object obj = clazz.newInstance();
        // 执行方法测试
        for (Method m : testList) {
            // 先执行@MyBefore的方法
            for (Method bm : beforeList) {
                bm.invoke(obj);
            }
            // 测试方法
            m.invoke(obj);
            // 最后执行@MyAfter的方法
            for (Method am : afterList) {
                am.invoke(obj);
            }
        }
    }

}
