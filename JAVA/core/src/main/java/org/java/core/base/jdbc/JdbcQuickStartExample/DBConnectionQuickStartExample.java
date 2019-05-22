package org.java.core.base.jdbc.JdbcQuickStartExample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionQuickStartExample {

	public static final String QUERY = "SELECT id, name, age FROM user";
	
	public static void main(String[] args) {

		// Java7的try-with-resources特性可以确保只要我们跳出try-catch块，资源就能被关闭
		// 使用try-with-resources to avoid closing resources
		// JDBC Connection，Statement和ResultSet是昂贵的资源，我们应该在完成使用后立即关闭它们。
		try(Connection con = DBConnection.getConnection("/db-resource.properties");
			// 创建Statement对象
			Statement stmt = con.createStatement();
			// 运行sql，得到结果集对象
			ResultSet resultSet = stmt.executeQuery(QUERY)){

			// 调用结果集的next()方法移动游标到第一行,
			// 然后连续的调用此方法，移动游标到结果集的下一行
			// 如果没有更多的行了，就返回false,跳出while循环.
			while(resultSet.next()) {
				// 使用结果集的getXXX(columnLabel(列名))方法获取每一列的值
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				System.out.println(id + "," + name + "," + age);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
