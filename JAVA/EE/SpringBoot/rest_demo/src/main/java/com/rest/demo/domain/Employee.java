package com.rest.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


// 需要使用@XmlRootElement使得可以:
// 1. 把格式为xml的请求体转换为Employee
// 2. 把Employee转化为xml格式

// 注意以前的xml配置中需要配置org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter类型的bean,现在基于注解的就不需要配置了.
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

	// @JsonSerialize将DateSerializer类用于从Java类型到JSON格式的Date转换.
	@JsonSerialize(using=DateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}