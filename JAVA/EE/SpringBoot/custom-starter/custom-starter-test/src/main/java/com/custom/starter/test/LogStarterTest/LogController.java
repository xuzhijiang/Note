package com.custom.starter.test.LogStarterTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogStarterService logStarterService;

    @ResponseBody
    @GetMapping("/logStarter")
    String testLogStarter(){
        logStarterService.work(2);
        logStarterService.core(3);
        logStarterService.test(4);
        return "custom start test ok";
    }

}

