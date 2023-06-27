package com.springboot.advanced.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 异步任务比较常用,比如发送邮件,不想阻塞当前线程.就可以使用异步发送
@RestController
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("/asyncSendMsg")
    public String hello() {
        asyncService.sendMsg();
        return "短信已发送";
    }
}
