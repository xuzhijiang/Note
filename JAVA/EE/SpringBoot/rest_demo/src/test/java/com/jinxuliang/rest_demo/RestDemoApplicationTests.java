package com.jinxuliang.rest_demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestDemoApplicationTests {

    //自动注入WebApplication上下文对象
    @Autowired
    private WebApplicationContext context;

    //用于测试Spring Boot的控制器
    private MockMvc mockMvc;

    @Before
    public void setup(){
        //构建虚拟MVC环境
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contextLoads() {
        if(context!=null){
            System.out.println("contextLoads：WebApplicationContext成功注入");
        }
        else{
            System.out.println("contextLoads：未能注入WebApplicationContext");
        }
    }

    //对控制器中各方法的单元测试代码

    @Test
    public void testHello() throws Exception {
       String result= mockMvc.perform(MockMvcRequestBuilders.get(
               "/myservice/hello")) //发出Get请求
                .andExpect(MockMvcResultMatchers.status().isOk()) //测试状态码
                //测试返回json字符串的特定属性的值是否是预期值
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("1"))
                //获取完整响应结果
                .andReturn().getResponse().getContentAsString();
       //输出方法返回值
        System.out.println(result);
    }

    //测试包容查询参数的HTTP请求：/myservice/query?info=Hello
    @Test
    public void testQueryArgument() throws Exception {
        String arguInfo="Hello";
        String result= mockMvc.perform(MockMvcRequestBuilders.get(
                "/myservice/query")
                .param("info",arguInfo)) //发出带有查询参数的Get请求
                .andExpect(MockMvcResultMatchers.status().isOk()) //测试状态码
                //获取完整响应结果
                .andReturn().getResponse().getContentAsString();
        //输出HTTP响应
        System.out.println(result);
        Assert.isTrue(result.equals(arguInfo),"return value should ="+arguInfo);
    }

    //测试抛出404异常
    @Test
    public void test404Error() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/notExisted"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}

