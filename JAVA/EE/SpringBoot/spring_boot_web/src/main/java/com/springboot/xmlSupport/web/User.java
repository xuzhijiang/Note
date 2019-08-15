package com.springboot.xmlSupport.web;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// @Data、@NoArgsConstructor、@AllArgsConstructor是lombok简化代码的注解，主要用于生成get、set以及构造函数。
@Data
@NoArgsConstructor
@AllArgsConstructor
// 第二步: 定义Xml内容对应的Java对象
@JacksonXmlRootElement(localName = "User")
public class User {

    // @JacksonXmlRootElement、@JacksonXmlProperty注解是用来维护对象属性在xml中的对应关系。
    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "age")
    private Integer age;

}
