package org.java.core.advanced.proxy.StaticProxy;

public class StaticProxyTest {
    public static void main(String[] args) {
        Calculator target = new CalculatorImpl();
        Calculator proxy = new Proxy(target);
        proxy.add(1, 2);
    }
}

interface Calculator {
    int add(int a,int b);
}

/**
 * 目标类
 */
class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

/**
 * 代理类
 */
class Proxy implements Calculator {

    private Calculator target;

    public Proxy(Calculator target) {
        this.target = target;
    }

    @Override
    public int add(int a, int b) {
        pre();
        int result = target.add(a, b);
        after();
        return result;
    }

    private void after() {
        System.out.println("**********after");
    }

    private void pre() {
        System.out.println("************pre");
    }
}