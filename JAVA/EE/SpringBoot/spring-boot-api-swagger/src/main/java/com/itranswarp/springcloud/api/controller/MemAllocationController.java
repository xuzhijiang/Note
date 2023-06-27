package com.itranswarp.springcloud.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/allocate")
public class MemAllocationController {

    private static final int _1M = 1024 * 1024;

    private AtomicInteger count = new AtomicInteger();

    @GetMapping(value = "/mem")
    public String allocationMem(@RequestParam(value = "num") int num,
                                @RequestParam("size") int size) {
        for (int i = 0; i < num; i++) {
            byte[] byteArray = new byte[size * _1M];
            System.out.println("第 " + count.incrementAndGet() + " 次分配内存,大小为: " + size + "M");
        }
        return "allocate success";
    }
}
