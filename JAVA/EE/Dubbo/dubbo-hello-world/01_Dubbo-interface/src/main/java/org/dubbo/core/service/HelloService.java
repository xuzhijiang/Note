package org.dubbo.core.service;

// dubbo-interface 后面被打成 jar 包，它的作用只是提供接口

// 点击右边的 Maven Projects 然后选择 install ，这样 jar就安装到了本地
public interface HelloService {
    String sayHello(String name);
}
