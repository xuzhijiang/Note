package org.java.core.base.annotations.customJunitFramework;

/**
 * 自定义Junit注解使用类
 */
public class JunitTestSimplePojo {

    @MyBefore
    public void setUp() {
        System.out.println("do set up......");
    }

    @MyTest
    public void save() {
        System.out.println("do save......");
    }

    @MyTest
    public void delete() {
        System.out.println("do delete......");
    }

    @MyAfter
    public void tearDown() {
        System.out.println("do tear down......");
    }

}
