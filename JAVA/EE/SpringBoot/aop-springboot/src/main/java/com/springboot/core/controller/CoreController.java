package com.springboot.core.controller;

import com.springboot.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * http://localhost:8080/hello?age=30，观察日志
 */
@RestController
public class CoreController {

    @Autowired
    private PersonService personService;

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("age") int age){
        return "my age is: " + age;
    }

    @ResponseBody
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name){
        return personService.add() + " my name is: " + name;
    }

}
