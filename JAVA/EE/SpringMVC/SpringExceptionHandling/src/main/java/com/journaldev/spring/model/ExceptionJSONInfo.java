package com.journaldev.spring.model;

// 由于我们也将返回JSON响应，让我们创建一个带有异常详细信息的java bean，它将作为响应发送。
public class ExceptionJSONInfo {

	private String url;
	private String message;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
