package com.didispace.web;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 第二步: 定义Xml内容对应的Java对象

// 其中：@Data、@NoArgsConstructor、@AllArgsConstructor是lombok简化代码的注解，主要用于生成get、set以及构造函数。

// @JacksonXmlRootElement、@JacksonXmlProperty注解是用来维护对象属性在xml中的对应关系。

// 上述配置的User对象，其可以映射的Xml样例如下

/**
 * <User>
 * 	<name>aaaa</name>
 * 	<age>10</age>
 * </User>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "User")
public class User {

    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "age")
    private Integer age;

}
