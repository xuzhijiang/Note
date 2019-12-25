package org.java.core.advanced.DesignPatterns.SixPrinciples.singleResponsibility;

public class Client {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.breathe("🐖");
        animal.breathe("羊");
        animal.breathe("马");

        // animal.breathe("鱼"); // 问题来了

        /**
         * 方案一: 新增一个类,这样的弊端是以后如果再有一个其他动物,也要新增
         */
        // WaterAnimal waterAnimal = new WaterAnimal();
        // waterAnimal.breathe("鱼");

        animal.breatheWater("鱼");
    }
}
