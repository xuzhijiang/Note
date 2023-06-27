package org.mq.core.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mq.core.server.business.TicketBusiness;
import org.mq.core.server.dao.TicketMapper;
import org.mq.core.server.domain.ZgTicket;
import org.mq.core.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

// 直接向12306发起请求 和 把请求发送到mq, 然后12306从mq慢慢按照自己的速度 获取请求, 一个个处理
// 这两种情况的 处理能力是有天壤之别的.
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TicketTest {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    TicketBusiness ticketBusiness;

//    private static int count = 10;
    private static int count = 3000;

    private CountDownLatch countDownLatch = new CountDownLatch(count);

    // 高并发测试
    @Test
    public void testHighConcurrent() {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                // 不让你执行,让你等在这里
                try {
                    // count个线程全部等在这里
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // count个线程一起去抢票
                ticketBusiness.process("OK");
                log.info("当前线程: " + Thread.currentThread().getName() + "抢票结束");
            }, "Thread-num-" + i).start();
            // 如果创建成功一个线程,我们就把发令枪减一
            countDownLatch.countDown();
            System.out.println("count: " + i);
        }

        try {
            // 上面的子线程里面要进行大量的 耗时运算,
            // 当主线程处理完成后,要用到子线程的处理结果,这个时候就要用到join()方法了.
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
