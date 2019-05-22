package org.java.core.base.jdbc.StmtAndPrepStmt;

import org.java.core.base.jdbc.JdbcQuickStartExample.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrepareStatementExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String email = "xzj@qq.com' or '1'='1";// 只知道用户的email
		String pwd = "";

		String query = "select id, name, age, email, pwd from user where email = ? and password = ?";

		Connection connection = null;
		try {
			connection = DBConnection.getConnection("/db-resource.properties");
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			System.out.println("Prepared Statement before bind variables" + preparedStatement.toString());
			// set the parameter
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, pwd);
			// 内部会将特殊字符转义
			// select id, name, age, email, pwd from user where email = 'xzj@qq.com\' or \'1\'=\'1' and password = ''
			System.out.println("Prepared Statement after bind variables set" + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery(query);

			while (rs.next()) {
				System.out.println("Name=" + rs.getString("name")
						+ ",email=" + rs.getString("email")
						+ ",pwd=" + rs.getString("pwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close
		}
	}

}
