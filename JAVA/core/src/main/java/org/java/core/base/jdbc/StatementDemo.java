package org.java.core.base.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDemo {

	public static void main(String[] args) {
		String email = "'xxx@qq.com'";

		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		try{
			conn = JDBCUtils.getConnection();
			// 获取执行sql语句的Statement对象
			stmt = conn.createStatement();
			String sql = "select id, username, password, email from tb_user where email = " + email;
			// 执行sql，得到结果集对象
			resultSet = stmt.executeQuery(sql);
			// 调用结果集的next()方法移动游标到第一行,
			// 如果没有更多的行了，就返回false,跳出while循环.
			while(resultSet.next()) {
				// 使用结果集的getXXX(columnLabel(列名))方法获取每一列的值
				int id = resultSet.getInt("id");
				String name = resultSet.getString("username");
				System.out.println(id + "," + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(resultSet, stmt, conn);
		}
	}

}
