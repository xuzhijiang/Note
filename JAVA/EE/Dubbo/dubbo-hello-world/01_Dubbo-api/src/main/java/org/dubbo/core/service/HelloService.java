package org.dubbo.core.service;

// dubbo-api：存放所有公共接口的模块，服务提供方与服务消费方均依赖此模块

// dubbo-api 后面被打成 jar 包，它的作用只是提供接口

// 点击右边的 Maven Projects 然后选择 install ，这样 jar就安装到了本地
public interface HelloService {
    String sayHello(String name);
}
