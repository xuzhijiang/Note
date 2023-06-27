package com.springboot.web.restful.curd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
// 固定窗口
@Component
@Slf4j
public class FixedWindow {

    private long start = System.currentTimeMillis();
    private static final long interval = 5000L; // 固定的时间窗口ms
    private int reqCount = 0;
    private static final int MAX_LIMIT = 2; //  固定时间窗口内最大请求数
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public boolean isPermit() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < interval + start) {
            reqCount++;
            return reqCount <= MAX_LIMIT;
        } else {
            start = currentTimeMillis;
            reqCount = 1;
            return true;
        }
    }

}
