package com.springboot.beanscope.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// Spring other singleton scope(单例scope)，始终只有一个实例
// 请注意，singleton是默认范围，因此我们可以删除@Scope(value =“singleton”）。
@Component
@Scope("singleton")
public class Singleton {}