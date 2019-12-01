package com.myspringframework.boot.web;

import org.apache.catalina.startup.Tomcat;

public class MySpringBootApplication {

    public static void run() {
        // 我们模仿springboot,导入了内嵌的tomcat,然后自己启动tomcat服务器.
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        try {
            tomcat.addWebapp("/", "D:\\");

            tomcat.start();

            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("容器启动失败");
        }
    }
}
