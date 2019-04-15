package org.java.core.base.ObjectClass;

public class ObjectMain {

    public static void main(String[] args){
        // 得到的是ObjectMain.class，然后转成toString输出
        System.out.println(new ObjectMain().getClass());
        // true
        System.out.println(new ObjectMain().getClass() == ObjectMain.class);
    }
}
