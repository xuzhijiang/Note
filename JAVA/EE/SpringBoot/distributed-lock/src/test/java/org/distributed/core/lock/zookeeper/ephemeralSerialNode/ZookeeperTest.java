package org.distributed.core.lock.zookeeper.ephemeralSerialNode;

import com.distributed.lock.zookeeperlock.ephemeralSerialNode.Lock;
import com.distributed.lock.zookeeperlock.ephemeralSerialNode.ZookeeperLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZookeeperTest {

    ZookeeperLock zookeeperLock;

    static long count = 0L;

    static String PARENT_PATH = "/tuling-lock";

    @Before
    public void init() {
        zookeeperLock = new ZookeeperLock();
        zookeeperLock.createParentNode(PARENT_PATH);
    }

    @Test
    public void test() throws InterruptedException, IOException {
        zookeeperLock.lock("nana", 365*24*3600*1000);
        System.in.read();
    }

    @Test
    public void run() throws IOException, InterruptedException {
        File file = new File("d:/test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 排着队给file这个文件+1
        for (int i = 1; i <= 1000; i++) {
            executorService.submit(() -> {
                // 注意这个时间要给够,否则锁会出现问题
                Lock lock = zookeeperLock.lock(file.getPath(), 600 * 1000);
                try {
                    log.info(Thread.currentThread().getName() + "=========>获取锁成功=======>" + lock.getPath());
                    String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
                    count = Integer.parseInt(firstLine);
                    count++;
                    System.out.println("---------------------------count: " + count);
                    log.info("---------------------------count: " + count);
                    Files.write(file.toPath(), String.valueOf(count).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    log.info(Thread.currentThread().getName() + "=========>释放锁=======>");
                    zookeeperLock.unlock(lock);
                }
            }, "Thread-num-" + i);
        }

        executorService.shutdown();
        // 会每隔2秒钟检查一次是否执行完毕（状态为 `TERMINATED`），当从 while 循环退出时就表明线程池已经完全终止了。
        while (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("线程还在运行");
            log.info("=========>线程还在运行");
        }
        String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
        System.out.println(firstLine);
        System.out.println(count);
    }

}
