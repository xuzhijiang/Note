package org.java.core.base.reflection;

import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtils {

    @Test
    public void printFieldMsg() {
        Class clazz = Object.class;
        /**
         * 成员变量也是对象
         * java.lang.reflect.Field
         * Field类封装了关于成员变量的操作
         */
        // getDeclaredFields()获取的是该类自己声明所有的成员变量的信息，包括public，private的等.
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

    @Test
    public void printConstructorMsg() {
        System.out.println("--------------------------");
        Class clazz = Object.class;
        /**
         * 构造函数也是对象
         * java.lang.reflect.Constructor
         */
//       Constructor[] constructors = clazz.getConstructors();
        // getDeclaredConstructors()获取的是该类自己声明的所有的构造方法，包括private
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

    @Test
    public void printMethodMsg() {
        Class clazz = Object.class;
        System.out.println("类的名称是：" + clazz.getName());
        /**
         * Method类，方法对象
         * java.lang.reflect.Method
         * 一个成员方法就是一个Method对象
         */
        // getMethods()方法获取的就是该class or interface的所有的public的方法，包括父类继承的public方法。
//		Method[] methods = clazz.getMethods();
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
