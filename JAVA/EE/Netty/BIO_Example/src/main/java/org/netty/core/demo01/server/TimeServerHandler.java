package org.netty.core.demo01.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

/**
 * worker Thread
 */
public class TimeServerHandler implements Runnable {

    private Socket clientProxy;

    public TimeServerHandler(Socket clientProxy){
        this.clientProxy = clientProxy;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(clientProxy.getInputStream()));
            writer = new PrintWriter(clientProxy.getOutputStream());
            // 因为一个client可以发送多次请求，这里的每一次循环，相当于接收处理一次请求
            while(true) {
                // Socket的getInputStream方法返回的InputStream对象的read方法就是Blocking IO的
               String request = reader.readLine();
               if(!"GET CURRENT TIME".equals(request)) {
                   writer.println("BAD_REQUEST");
               } else {
                   writer.println(Calendar.getInstance().getTime().toLocaleString());
               }
               writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
                clientProxy.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
