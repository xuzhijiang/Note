package org.java.core.advanced.proxy.StaticProxy;

/**
 * 一个小需求：给Calculator原有方法添加日志打印
 *
 * 使用静态代理实现日志打印
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        Calculator calculator = new CalculatorProxy(new CalculatorImpl());
        calculator.add(1, 2);
        calculator.subtract(2, 3);
    }
}

interface Calculator {
    int add(int a,int b);
    int subtract(int a,int b);
}

/**
 * 目标对象实现类
 */
class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }
}

/**
 * 代理对象实现类
 */
class CalculatorProxy implements Calculator {

    //代理对象内部维护一个目标对象引用
    private Calculator target;

    //代理对象内部维护一个目标对象引用
    public CalculatorProxy(Calculator target) {
        this.target = target;
    }

    //调用目标对象的add，并在前后打印日志
    @Override
    public int add(int a, int b) {
        System.out.println("add call");
        return target.add(a, b);
    }

    //调用目标对象的add，并在前后打印日志
    @Override
    public int subtract(int a, int b) {
        System.out.println("subtract call");
        return target.subtract(a, b);
    }
}