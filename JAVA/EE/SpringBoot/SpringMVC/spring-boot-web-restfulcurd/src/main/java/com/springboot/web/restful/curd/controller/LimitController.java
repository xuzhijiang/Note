package com.springboot.web.restful.curd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class LimitController {

    @Autowired
    FixedWindow fixedWindow;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @GetMapping("/seckill/product/{productId}")
    public String seckillProduct(@PathVariable("productId") Long productId) {
        if (fixedWindow.isPermit()) {
            log.error("===>>>秒杀成功,当前的时间====>>>> " + sdf.format(new Date()));
            return "秒杀成功==========>>>> " + sdf.format(new Date());
        } else {
            log.error("===>>>拒绝访问,当前的时间====>>>> " + sdf.format(new Date()));
            return "拒绝访问==========>>>> " + sdf.format(new Date());
        }
    }
}
