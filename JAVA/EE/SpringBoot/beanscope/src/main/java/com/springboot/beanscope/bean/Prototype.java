package com.springboot.beanscope.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// spring bean原型scope，每次请求都实例化一个对象。
@Component
@Scope("prototype")
public class Prototype {}