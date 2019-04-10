package org.java.core.base.reflection.part2;

/**
 * Class： 所有类的根源,是整个Java反射机制的源头。
 * Object：所有对象的根源。Object.class也是Class的一个实例对象.
 *
 * Class类的作用:
 *
 * 在运行时判断任意一个对象所属的类
 * 在运行时构造任意一个类的对象->clazz.newInstance();
 * 在运行时判断任意一个类所具有的成员变量和方法->clazz.getDeclaredFields(), clazz.getDeclaredMethods()
 * 在运行时调用任意一个对象的方法->Method method = clazz.getMethod(),method.invoke(obj, args...);
 * 生成动态代理
 *
 * 通过代码＋注释来驱动讲解Java的反射知识，这样比纯文字描述了解的要快而且记得牢的多
 */
public class ClassDemo {
    public static void main(String[] args) {

        /**
         * Book也是一个实例对象
         * 任何一个类都是Class的实例对象，这个实例对象有三种表示方式
         */
        Book book = new Book();

        /**
         * 第一种表示方式－－>实际在告诉我们任何一个类都有一个隐含的静态成员变量class
         */
        Class clazz1 = Book.class;

        /**
         * 第二种表示方式－－>已经知道该类的对象，通过getClass()获取
         */
        Class clazz2 = book.getClass();

        /**
         * clazz1、clazz2在官网里称为Book类的类型
         * 万事万物皆对象,类也是对象，是Class类的实例对象
         */

        /**
         * 一个类只可能是Class类的一个实例对象，无论采用哪种方式获取返回的都是同一个对象。
         */
        System.out.println(clazz1 == clazz2);

        /**
         * 第三种表示方式－－>不仅表示类的类类型，还代表了动态加载类
         * 编译时刻加载类是静态加载类，运行时刻加载类是动态加载
         */
        Class clazz3 = null;
        try {
            clazz3 = Class.forName("org.java.core.base.reflection.part2.Book");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(clazz1 == clazz3);

        try {
            //我们完全可以通过类(本质上也就是一个对象)的类型(也就是Class),创建该类的实例对象
            Book book1 = (Book)clazz1.newInstance();//需要有无参数的构造方法
            book1.getBookInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ClassUtils.printFieldMsg(book);
        //ClassUtils.printConstructorMsg(book);
        ClassUtils.printMethodMsg(book);
    }

}
