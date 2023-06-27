package com.SourcePracticeDemo.controller;

import com.SourcePracticeDemo.service.ContentService;
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
}

