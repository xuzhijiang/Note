package org.java.core.advanced.DesignPatterns.structural.adapter.demo;

/**
 * 适配器模式
 *
 * 适配器模式比较好理解。像生活中插线口的插头有2个口的，也有3个口的。如果电脑的电源插口只有3个口的，但是我们需要一个2个口的插口的话，这个时候就需要使用插座来外接这个3个口的插头，插座上有2个口的插头。
 *
 * 这个例子跟我们编程一样，当用户系统的接口跟我们系统内部的接口不一致时，我们可以使用适配器来完成接口的转换。
 *
 * 使用继承的方式实现类的适配:
 */
public class Adapter extends Source implements Targetable {
    @Override
    public void newMethod() {
        System.out.println("new method");
    }

    /**
     * 上述方式是用接口和继承的方式实现适配器模式。
     * 当然我们也可以使用组合的方式实现(把Source当成属性放到Adapter中)。
     */
    public static void main(String[] args) {
        Targetable targetable = new Adapter();
        targetable.method();
        targetable.newMethod();
    }
}
