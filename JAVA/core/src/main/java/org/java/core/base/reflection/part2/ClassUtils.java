package org.java.core.base.reflection.part2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtils {

    public static void printFieldMsg(Object object) {
        Class clazz = object.getClass();

        /**
         * 成员变量也是对象
         * java.lang.reflect.Field
         * Field类封装了关于成员变量的操作
         */
        // getFields()获取的是这个类所有的Public的成员变量的信息
        // Field[] fields = clazz.getFields();

        // getDeclaredFields()获取的是该类所有的成员变量的信息，包括public，private的等.
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("clazz contains fields number: " + fields.length);
        for (Field field : fields) {
            // 得到成员变量的类型的类类型
            Class fieldType = field.getType();
            String typeName = fieldType.getName();
            // 得到成员变量的名称
            String fieldName = field.getName();
            System.out.println(typeName + " - " + fieldName);
        }
    }

    public static void printConstructorMsg(Object object) {
        System.out.println("--------------------------");
        Class clazz = object.getClass();
        /**
         * 构造函数也是对象
         * java.lang.reflect.Constructor
         */

        // getConstructors()获取的是该类的所有public方法
//         Constructor[] constructors = clazz.getConstructors();

        // getDeclaredConstructors()获取的是该类所有的构造方法，包括private
        Constructor[] constructors = clazz.getDeclaredConstructors();
        System.out.println("constructors length: " + constructors.length);
        for (Constructor constructor : constructors) {
            System.out.print(constructor.getName() + "(");
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class paramClazz : paramTypes) {
                System.out.print(paramClazz.getName()+ " , " );
            }
            System.out.println(")");
        }
    }

    public static void printMethodMsg(Object object) {
        System.out.println("---------------");
        // 获取该对象的类类型
        Class clazz = object.getClass();
        System.out.println("类的名称是：" + clazz.getName());
        /**
         * Method类，方法对象
         *
         * java.lang.reflect.Method
         * 一个成员方法就是一个Method对象
         */

        // getMethods()方法获取的就是该class or interface的所有的public的方法，包括父类继承的public方法。
//		Method[] methods = clazz.getMethods();

        // getDeclaredMethods()获取的是该class or interface 自己声明的方法，不问访问权限。包括private的method
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 得到方法的返回值类型的类类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName() + " ");
            // 得到方法的名称
            System.out.print(method.getName() + "(");
            // 获取参数类型--> 得到的是参数列表的类类型
            Class[] paramTypes = method.getParameterTypes();
            for (Class paramClazz : paramTypes) {
                System.out.print(paramClazz.getName() + " , ");
            }
            System.out.println(")");
        }
    }

}
