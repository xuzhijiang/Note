package org.java.core.advanced.DesignPatterns.structural.facade;

import java.sql.Connection;

// 我们有两个helper接口，即MySqlHelper和OracleHelper。
public class MySqlHelper {

	public static Connection getMySqlDBConnection(){
		//get MySql DB connection using connection parameters
		return null;
	}
	
	public void generateMySqlPDFReport(String tableName, Connection con){
		//get data from table and generate pdf report
	}
	
	public void generateMySqlHTMLReport(String tableName, Connection con){
		//get data from table and generate pdf report
	}
	
}
