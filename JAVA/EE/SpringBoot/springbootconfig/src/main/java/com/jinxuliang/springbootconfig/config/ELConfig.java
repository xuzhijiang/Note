package com.jinxuliang.springbootconfig.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
//指明配置文件所在位置，其内容会被抽取出来，放到环境变量中
@PropertySource("classpath:test.properties")
public class ELConfig {

    //注入普通字符串
    @Value("this is a string constant")
    String normal;

    //注入操作系统属性
    @Value("#{systemProperties['os.name']}")
    String osName;

    //注入表达式结果
    @Value("#{T(java.lang.Math).random() *100}")
    double randomNumber;
    //注入环境变量(会自动地从test.properties中提取数据）
    @Autowired
    Environment environment;
    //注入文件资源
    @Value("classpath:test.txt")
    private Resource testFile;
    //注入自定义properties文件中的值
    @Value("${book.name}")
    private String bookName;

    public void printFields() throws IOException {
        System.out.println(normal);
        System.out.println(osName);
        System.out.println(randomNumber);
        System.out.println(IOUtils.toString(testFile.getInputStream(),
                Charset.forName("UTF-8")));
        System.out.println(bookName);
        System.out.println(environment.getProperty("book.author"));
    }
}
