package org.java.core.DesignPatterns.singleton;

import java.io.IOException;
import java.util.Properties;

// 饿汉式：
public class Singleton3 {

    // 为什么要用private修饰?思考?
    // 因为这个实例的初始化需要传参数,所以不能被外界随意获得.
    private static final Singleton3 INSTANCE;

    private String info;

    static {
        try {
            Properties prop = new Properties();

            prop.load(Singleton3.class.getClassLoader().getResourceAsStream("singleton.properties"));

            INSTANCE = new Singleton3(prop.getProperty("info"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Singleton3(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public static Singleton3 getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "Singleton3{" +
                "info='" + info + '\'' +
                '}';
    }
}
