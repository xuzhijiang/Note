package org.java.core.base.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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

    public static void testCalendar(int fixedHours,int fixedMinutes,int fixedSeconds) {
        System.out.println("---result: " + TimeUnit.HOURS.toMillis(4L));

        Calendar date = Calendar.getInstance();
        System.out.println("------date: " + date.get(Calendar.YEAR));
        System.out.println("------date: " + date.get(Calendar.MONTH));
        System.out.println("-------date: " + date.get(Calendar.DATE));
        System.out.println("-------date: " + date.getTime());
        System.out.println("-------date: " + date.getTimeZone());
        System.out.println("--------date: " + date.get(Calendar.HOUR_OF_DAY));
        System.out.println("--------date: " + date.get(Calendar.MINUTE));
        System.out.println("--------date: " + date.get(Calendar.SECOND));

        if (date.get(Calendar.HOUR_OF_DAY) > fixedHours) {
            int time = 24 - (date.get(Calendar.HOUR_OF_DAY) - fixedHours);
            // after time
            System.out.println("11111------------time: " + time);
        } else {
            int time = fixedHours - date.get(Calendar.HOUR_OF_DAY);
            // after time
            System.out.println("2222------------time: " + time);
        }

//		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 19, 0);
//		date.set(date., Calendar.MONTH, Calendar.DAY_OF_MONTH, 3, 0, 0);
    }

    public static long currentTimeToMillis() {
        Calendar date = Calendar.getInstance();
        int hours = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE);
        int seconds = date.get(Calendar.SECOND);
        System.out.println("------- HOUR: " + hours);
        System.out.println("------- MINUTE: " + minutes);
        System.out.println("------- SECOND: " + seconds);

        long hours2Seconds = TimeUnit.HOURS.toSeconds(hours);
        long minutes2Seconds = TimeUnit.MINUTES.toSeconds(minutes);
        System.out.println("------- HOUR to SECOND: " + hours2Seconds);
        System.out.println("------- MINUTE TO SECOND: " + minutes2Seconds);
        System.out.println("------- SECOND: " + seconds);

        long totalSeconds = hours2Seconds + minutes2Seconds + seconds;
        System.out.println("------------total seconds: " + totalSeconds);

        long totalMillis = TimeUnit.SECONDS.toMillis(totalSeconds);
        System.out.println("------------total millis: " + totalMillis);

        return totalMillis;
    }

    /**
     * 开机后，在一天中某一个固定时间做某件事情
     * @param hours
     * @param minutes
     * @param seconds
     */
    public static void startFixedTimeToDoSomethingTimer(int hours, int minutes, int seconds) {
        long fixedMillis = toMillis(hours, minutes, seconds);

        long currentMillis = toMillis(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND));

        long totalMillis = toMillis(24, 0, 0);

        long delayMillis = -1L;
        if (currentMillis > fixedMillis) {
            delayMillis = totalMillis - currentMillis + fixedMillis;
        } else {
            delayMillis = fixedMillis - currentMillis;
        }
        System.out.println("--------delayMillis: " + delayMillis);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // do sth
            }
        }, delayMillis);
    }

    /**
     * 将具体的时分秒转换成毫秒
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static long toMillis(int hours, int minutes, int seconds) {
        long total = TimeUnit.HOURS.toMillis(hours) + TimeUnit.SECONDS.toMillis(seconds) + TimeUnit.MINUTES.toMillis(minutes);
        return total;
    }
}
