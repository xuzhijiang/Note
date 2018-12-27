package com.journaldev.spring.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

// 一个简单的POJO类，它将作为Restful Web服务方法的输入和输出。

// 唯一需要注意的重点是@JsonSerialize注释的使用，
// 它将DateSerializer类用于从Java类型到JSON格式的Date转换.
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
	
	@JsonSerialize(using=DateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
