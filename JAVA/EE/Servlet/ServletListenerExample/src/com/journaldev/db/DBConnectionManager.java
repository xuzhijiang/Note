package com.journaldev.db;

import java.sql.Connection;

// DBConnectionManager：这是数据库连接的类，为简单起见，
// 我没有提供实际数据库连接的代码。 我们将此对象设置为servlet上下文的属性。

public class DBConnectionManager {

	private String dbURL;
	private String user;
	private String password;
	private Connection con;
	
	public DBConnectionManager(String url, String u, String p){
		this.dbURL=url;
		this.user=u;
		this.password=p;
		//create db connection now
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	public void closeConnection(){
		//close DB connection here
	}
}
