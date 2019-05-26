package org.dubbo.core;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 测试: http://localhost:9099/hello
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerApplication {

    // 注意：在主类中，不能直接mapping url，就是把HelloController中的内容
    // 搬过来，会导致dubbo不去连接zookeeper,原因未知.
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

}
