package com.jinxuliang.rest_client_demo;

import com.jinxuliang.rest_demo.domain.MyClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClientDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestClientDemoApplication.class, args);

        System.out.println("Hello");

        testRestTemplate();

    }

    private static void testRestTemplate(){
        RestTemplate template=new RestTemplate();
        MyClass result=template.getForObject("http://localhost:8080/myservice/hello", MyClass.class);
        System.out.println(result);
    }
}
