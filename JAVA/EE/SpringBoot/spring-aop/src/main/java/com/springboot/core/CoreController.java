package com.springboot.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class CoreController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("age") int age){
        return "my age is: " + age;
    }

    @ResponseBody
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name){
        return " my name is: " + name;
    }

}
