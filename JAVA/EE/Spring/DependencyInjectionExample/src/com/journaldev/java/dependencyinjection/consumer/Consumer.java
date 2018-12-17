package com.journaldev.java.dependencyinjection.consumer;

// Java Dependency Injection – Service Consumer
// We are not required to have base interfaces for consumer 
// classes but I will have a Consumer interface declaring contract for consumer classes.

// 消费者类应该根据服务接口来编写。
public interface Consumer {

	void processMessages(String msg, String rec);
}
