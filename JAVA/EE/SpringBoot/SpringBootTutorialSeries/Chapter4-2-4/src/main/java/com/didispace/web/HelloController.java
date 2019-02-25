package com.didispace.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    /**
     * 过传入name参数，返回“hello xxx”的功能。
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

}
