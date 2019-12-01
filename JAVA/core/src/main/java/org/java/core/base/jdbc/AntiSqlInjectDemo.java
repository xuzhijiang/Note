package org.java.core.base.jdbc;

import java.sql.*;

// PreparedStatement能够解决sql注入的问题，它扩展了Statement并在执行查询之前自动转义特殊字符
public class AntiSqlInjectDemo {

	public static void main(String[] args) {
		// 只知道用户的email
		String email = "xxx@qq.com";
		String password = "'a' or 'a' = 'a'";

		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			conn = JDBCUtils.getConnection();

			String sql = "select id, username, password, email from tb_user where email = ? and password = ?";

			// 预编译sql语句，得到PreparedStatement对象
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);

			// 内部会将特殊字符'进行转义
			// select id, username, password, email from tb_user where email = 'xxx@qq.com' and password = '\'a\' or \'a\' = \'a\''
			// 所以 password = '\'a\' or \'a\' = \'a\'' 除了最外边的2个'是起作用,内部的都会当成普通字符串
			System.out.println("ps: " + ps.toString());

			// 执行executeQuery(),得到结果集：ResultSet
			rs = ps.executeQuery();

			// 得到结果集的元数据：ResultSetMetaData
			ResultSetMetaData metaData = rs.getMetaData();

			// 通过ResultSetMetaData得到columnCount,columnLabel；
			int columnCount = metaData.getColumnCount();
			System.out.println("columnCount: " + columnCount);
			while (rs.next()) {
				System.out.println("Name=" + rs.getString("username")
						+ ",email=" + rs.getString("email")
						+ ",pwd=" + rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
	}

}
