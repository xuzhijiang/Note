package com.listener.core.db;

public class DBConnectionManager {

	private String dbURL;
	private String user;
	private String password;

	public DBConnectionManager(String url, String u, String p){
		this.dbURL=url;
		this.user=u;
		this.password=p;
	}
	
	public void closeConnection(){
	}
}
