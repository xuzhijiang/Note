package org.java.core.advanced.DesignPatterns.structural.facade;

import java.sql.Connection;

// 现在让我们看看客户端代码，不使用Facade模式和使用Facade模式接口:

// 正如您所看到的，使用Facade模式interface是一种更容易和更简洁的方法，
// 以避免在客户端有很多逻辑。获取数据库连接的JDBC Driver Manager类是外观(Facade)设计模式的一个很好的例子。
public class FacadePatternTest {

	public static void main(String[] args) {
		String tableName="Employee";
		
		//generating MySql HTML report and Oracle PDF report without using Facade
		Connection con = MySqlHelper.getMySqlDBConnection();
		MySqlHelper mySqlHelper = new MySqlHelper();
		mySqlHelper.generateMySqlHTMLReport(tableName, con);
		
		Connection con1 = OracleHelper.getOracleDBConnection();
		OracleHelper oracleHelper = new OracleHelper();
		oracleHelper.generateOraclePDFReport(tableName, con1);
		
		//generating MySql HTML report and Oracle PDF report using Facade
		HelperFacade.generateReport(HelperFacade.DBTypes.MYSQL, HelperFacade.ReportTypes.HTML, tableName);
		HelperFacade.generateReport(HelperFacade.DBTypes.ORACLE, HelperFacade.ReportTypes.PDF, tableName);
	}
	
}
