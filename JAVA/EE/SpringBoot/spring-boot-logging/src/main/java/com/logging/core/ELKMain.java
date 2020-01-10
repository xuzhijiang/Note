package com.logging.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ELKMain {

    private static final Logger logger = LoggerFactory.getLogger(ELKMain.class);

    public static final String[] VISIT = new String[]{
            "浏览页面", "评论商品", "加入收藏", "加入购物车", "提交订单", "使用优惠券", "领取优惠券", "搜索", "查看订单"
    };

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ELKMain.class, args);
        while (true) {
            int sleep = new Random().nextInt(10);
            Thread.sleep(TimeUnit.SECONDS.toMillis(sleep));
            int visitId = new Random().nextInt(VISIT.length);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String dateStr = sdf.format(new Date());

            int maxUserId = 9999;
            long userId = new Random().nextInt(9999);
            String result = "DAU|" + userId + "|" + VISIT[visitId] + "|" + dateStr;
            logger.info(result);
            System.out.println(logger);
        }
    }
}
