package com.SourcePracticeDemo.controller;

import com.SourcePracticeDemo.service.ContentService;
import com.SourcePracticeDemo.service.LogStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mvc")
public class MVCDemoController {

    @Autowired
    private ContentService contentService;

    @ResponseBody
    @GetMapping("/contentService")
    String contentService(){
        contentService.doSomething();
        return contentService.toString();
    }

    @Autowired
    private LogStarterService logStarterService;

    @ResponseBody
    @GetMapping("/customStarter")
    String testCustomStarter(){
        logStarterService.work(2);
        logStarterService.core(3);
        logStarterService.test(4);
        return "custom start test ok";
    }

}

