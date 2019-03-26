package org.redis.core;

import redis.clients.jedis.Jedis;

/**
 * Jedis作为推荐的java语言的redis客户端，其抽象封装为三部分：
 *
 * 对象池设计：Pool，JedisPool，GenericObjectPool，BasePoolableObjectFactory，JedisFactory
 *
 * 面向用户的redis操作封装：BinaryJedisCommands，JedisCommands，BinaryJedis，Jedis
 *
 * 面向redis服务器的操作封装：Commands，Client，BinaryClient，Connection，Protocol
 *
 * 类名	职责
 * Pool	抽象Jedis对象池操作；并委托给操作给GenericObjectPool
 * JedisPool	实现Pool并提供JedisFactory工厂
 * JedisFactory	实现BasePoolableObjectFactory，提供创建，销毁Jedis方法
 * BinaryJedisCommands	抽象面向客户端操作的Redis命令；key，value都为序列化后的byte数组
 * JedisCommands	抽象面向客户端操作的Redis命令；提供String类型的key，value
 * BinaryJedis	实现BinaryJedisCommands接口，并将实际操作委托给Client
 * Jedis	实现JedisCommands接口，并将操作委托给Client
 * Commands	抽象Redis操作接口，提供String类型的key，value操作；被Jedis调用
 * Connection	抽象了Redis连接；包括host，port，pass，socket，inputstream,outputstream,protocol 完成与Redis服务器的通信
 * Protocol	抽象了Redis协议处理
 * BinaryClient	继承Connection类，封装了基于Byte[]的key，value操作
 * Client	继承BinaryClient同时实现了Commands，对上层提供基于String类型的操作
 */
public class HelloRedis {

    /**
     * 如果你出现了异常ConnectionTimeoutException，连接超时异常，那么可能是：
     * 1、 因为你的ip地址输错，使用ifconfig可以查看你的linux的ip地址信息
     * 2、 如果不是第一个原因，则可能是因为你的服务器防火墙没有针对6379端口开放，
     * redis默认监听的就是这个端口，为了方便，可以选择直接关闭防火墙.命令为：service iptables stop
     *
     * 3、如果redis-server不是在6379端口启动，那么使用redis-cli连接的时候需要指定端口号。
     * 同理，这种情况下，使用java操作redis，也要指定端口等信息。
     */
    public static void main(String[]  args){
        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis("localhost");//当 new的时候，就与服务器建立了连接
        System.out.println("Connection to server sucessfully");
        // check whether server is running or not
        System.out.println("Server is running: " + jedis.ping());
    }
}
