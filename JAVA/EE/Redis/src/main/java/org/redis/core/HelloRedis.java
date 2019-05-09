package org.redis.core;

import redis.clients.jedis.Jedis;

public class HelloRedis {

    /**
     * 如果你出现了异常ConnectionTimeoutException，连接超时异常，那么可能是：
     * 可能是因为你的服务器防火墙没有针对6379端口开放，
     * redis默认监听的就是这个端口，为了方便，可以选择直接关闭防火墙.命令为：service iptables stop
     */
    public static void main(String[]  args){
        //Connecting to Redis server on localhost
        //当 new的时候，就与服务器建立了连接，默认是6379端口
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        // check whether server is running or not
        System.out.println("Server is running: " + jedis.ping());
    }
}
