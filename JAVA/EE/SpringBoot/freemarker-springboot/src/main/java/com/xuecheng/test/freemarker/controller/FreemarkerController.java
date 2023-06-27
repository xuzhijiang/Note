package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-12 18:40
 **/
@RequestMapping("/product")
@Controller
@Slf4j
public class FreemarkerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map){
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.put("model",body);
        return "index_banner";
    }

    @RequestMapping("/detail/{productId}")
    public String detail(@PathVariable("productId") Long productId, Map<String, Object> map){
        //向数据模型放数据
        map.put("name","黑马程序员");
        Student stu1 = new Student("小明", 18, new Date(), 1000.86f);
        Student stu2 = new Student("小红", 19, new Date(), 200.1f, Arrays.asList(stu1), stu1);

        //准备map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);

        //向数据模型放数据
        map.put("stus", Arrays.asList(stu1, stu2));
        map.put("stu1",stu1);
        map.put("stuMap",stuMap);

        log.info("freemarker进行模板渲染=======>>>>>>> " + "test1.ftl");
        return "test1";
    }
}
