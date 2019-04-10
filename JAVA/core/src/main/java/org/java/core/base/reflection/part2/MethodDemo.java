package org.java.core.base.reflection.part2;

import java.lang.reflect.Method;

public class MethodDemo {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Book book = new Book("魔女闹江湖", "顾漫");
        // 第一步:获取类类型
        Class clazz = book.getClass();
        System.out.println("未修改之前书名为：" + book.getBookName());

        // 第二步:获取方法
        Method method;
        try {
            method = clazz.getMethod("setBookName", String.class);
            // 第三步：调用setBookName(String bookName)方法
            method.invoke(book, "微微一笑很倾城");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("修改之后书名为：" + book.getBookName());
    }

}
