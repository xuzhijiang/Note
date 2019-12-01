package org.java.core.base.generics;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 通过Reflection来认识泛型的本质
 */
public class GenericAndReflection {
    public static void main(String[] args) {

        ArrayList mDataList1 = new ArrayList();

        /**
         * 我曾经见过的一个面试题：如何让ArrayList<String>类型的变量添加一个int类型的值，
         * 下面就以此为例来介绍如何通过反射了解集合泛型的本质
         */
        ArrayList<String> mDataList2 = new ArrayList<>();
        System.out.println(mDataList1.getClass() == mDataList2.getClass());

        /**
         * 返回true说明编译之后集合的泛型是去泛型话的
         * Java中集合的泛型，是防止错误输入的，只在编译阶段有效，绕过编译就无效了
         * 反射的操作都是编译之后的操作
         * 验证：我们可以通过方法的反射来操作，绕过编译
         */
        try {
            Method m = mDataList2.getClass().getMethod("add", Object.class);
            m.invoke(mDataList2, 10);
            m.invoke(mDataList2, 20);
            m.invoke(mDataList2, 30);
            System.out.println(mDataList2);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}