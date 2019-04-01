package org.java.core.advanced.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class JavassisTimingWrong {

    public static void main(String[] args) throws Exception {

        //test01();

//        test02();

        test03();
    }

    private static void test03() throws Exception{
        //需要修改的已有的类名和方法名

        String className="org.java.core.advanced.javassist.Looper";

        String methodName="loop";

        ClassPool classPool = ClassPool.getDefault();
        CtClass clazz = classPool.get(className);

        CtMethod method = clazz.getDeclaredMethod(methodName);

        method.addLocalVariable("start", CtClass.longType);
        method.insertBefore("start=System.currentTimeMillis();");

        method.insertAfter("System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");");

        //调用修改的Looper类的loop方法

        Looper looper = (Looper) clazz.toClass().newInstance();

        looper.loop();
    }

    private static void test02() throws Exception{
        // 需要修改的已有的类名和方法
        String className = "org.java.core.advanced.javassist.Looper";
        String methodName = "loop";

        //修改原有类的方法名为loop$impl
        CtClass clazz = ClassPool.getDefault().get(className);
        CtMethod method = clazz.getDeclaredMethod(methodName);
        String newName = methodName + "$impl";
        method.setName(newName);

        //使用原始方法名loop，定义一个新方法，在这个方法内部调用loop$impl
        CtMethod newMethod = CtNewMethod.make("public void "+methodName+"(){" +
                        "long start=System.currentTimeMillis();" +
                        ""+newName+"();" +//调用loop$impl
                        "System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");" +
                        "}"
                , clazz);
        clazz.addMethod(newMethod);

        //调用修改的Looper类的loop方法
        Looper looper = (Looper) clazz.toClass().newInstance();
        looper.loop();

    }

    public static void test01() throws Exception {
        //需要修改的已有的类名和方法名

        String className="org.java.core.advanced.javassist.Looper";

        String methodName="loop";

        ClassPool classPool = ClassPool.getDefault();
        CtClass clazz = classPool.get(className);

        CtMethod method = clazz.getDeclaredMethod(methodName);

        method.insertBefore("long start=System.currentTimeMillis();");

        method.insertAfter("System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");");

        //调用修改的Looper类的loop方法

        Looper looper = (Looper) clazz.toClass().newInstance();

        looper.loop();

    }
}
