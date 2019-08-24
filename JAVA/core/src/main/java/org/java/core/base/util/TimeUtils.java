package org.java.core.base.util;

import org.junit.Test;

import java.util.Date;

/**
 * 日期时间处理工具类
 */
public class TimeUtils {

    @Test
    public void timeMillisToDate() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        System.out.println(date);
        // 纳秒
        long nanoTime = System.nanoTime();
        System.out.println("Current nano time: "+nanoTime);
    }
}
