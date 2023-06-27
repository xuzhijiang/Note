package org.mq.core.meituan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mq.core.meituan.sender.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituanTest {

    private RestTemplate restTemplate = new RestTemplate();

    private int idCard = 5;

    // 3w并发都没问题
    int count = 2000;

    private CountDownLatch countDownLatch = new CountDownLatch(count);

    @Autowired
    RabbitSender rabbitSender;

    // 美团创建高并发订单测试
    // 这个是在美团这个app里面做的,这个和直接在12306直接购买是有区别的.
    // 美团的并发线程量 比如30w,只要12306的mq的队列可以放下就行,剩下的就是12306按照自己的速度处理就性了.
    @Test
    public void testHighConcurrent() {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 发送消息给美团的队列
                rabbitSender.sentMessage("mt-exchange", "sam.ticket.routeKey", "OK!");
            }).start();

            countDownLatch.countDown();
        }

        try {
            TimeUnit.MICROSECONDS.sleep(3000);
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
