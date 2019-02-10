package com.journaldev.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

// 一个简单的POJO类(即普通的Jave对象类)，它将作为Restful Web service methods的输入和输出。

// 我们知道，对于使用JAXB marshalling的类，
// 我们需要使用@XmlRootElement注释来注释它。 所以将它添加到我们的Employee模型类中。

// 我们的Spring应用程序将同时支持JSON和XML。它甚至支持带有JSON响应的XML请求，反之亦然。

@XmlRootElement
public class Employee implements Serializable {

	private static final long serialVersionUID = -7788619177798333712L;
	
	private int id;
	private String name;
	private Date createdDate;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	// 唯一需要注意的重点是@JsonSerialize注释的使用，
	// 它将DateSerializer类用于从Java类型到JSON格式的Date转换.
	@JsonSerialize(using=DateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}