package org.netty.core.demo01.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket client = null;

        try {
            client = new Socket("127.0.0.1", 9090);
            //站在内存的角度，output是从内存向外写的流
            writer = new PrintWriter(client.getOutputStream());
            // 站在内存角度,input是把外面的流读进内存
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // 每隔5秒发送一次请求
            while(true) {
                // 客户端发起连接
                writer.println("GET CURRENT TIME");
                writer.flush();
                // 发出去请求之后，会阻塞在这里，直到读到流
                String response = reader.readLine();
                System.out.println("Current Time: " + response);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
                client.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

}
