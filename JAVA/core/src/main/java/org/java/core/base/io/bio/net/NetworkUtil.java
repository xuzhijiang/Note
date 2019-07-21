package org.java.core.base.io.bio.net;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * - Socket：使用 TCP 协议实现网络通信
 * - Datagram：使用 UDP 协议实现网络通信。DatagramSocket：通信类,DatagramPacket：数据包类
 *
 * ,ServerSocket：服务器端类,Socket：客户端类,
 * 服务器ServerSocket和客户端Socket通过 InputStream 和 OutputStream 进行输入输出。
 */
public class NetworkUtil {

    @Test
    public void createInetAddress() throws UnknownHostException {
        // InetAddress 表示 IP 地址

        // 没有公有的构造函数，只能通过静态方法来创建实例
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");

        inetAddress = InetAddress.getByAddress("127.0.0.1".getBytes());
    }

    @Test
    public void readDataFromURL() throws IOException {
        URL url = new URL("http://www.taobao.com");

        // 字节流
        InputStream is = url.openStream();

        // 字符流
        // StandardCharsets.UTF_8.name() equals to Charset.forName("UTF-8")
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8.name());

        // 提供缓存功能
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }
}
