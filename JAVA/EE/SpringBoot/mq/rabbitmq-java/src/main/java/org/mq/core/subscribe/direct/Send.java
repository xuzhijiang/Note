package org.mq.core.subscribe.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.mq.core.util.ConnectionUtil;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "删除商品";
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.basicPublish(EXCHANGE_NAME, "insert", null, "插入商品".getBytes());
        System.out.println(" [x] Sent '" + "插入商品" + "'");

        channel.close();
        connection.close();
    }
}