package com.tuling.seckill.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZkConfig {
    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Autowired
    private ZookeeperWatcher zookeeperWatcher;

    @Bean
    public ZooKeeper createZookeeper() throws IOException {
        return new ZooKeeper(zookeeperAddress, 60000, zookeeperWatcher);
    }
}
