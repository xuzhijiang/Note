package org.java.core.base.jdbc.StatementAndPreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GetUserDetails {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// read user entered data
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter email id:");
		String id = scanner.nextLine();
		System.out.println("User id=" + id);
		System.out.println("Please enter password to get details:");
		String pwd = scanner.nextLine();
		System.out.println("User password=" + pwd);
		printUserData(id, pwd);
	}

	private static void printUserData(String id, String pwd) throws ClassNotFoundException, SQLException {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			String query = "select name, country, password from Users where email = '" + id + "' and password='" + pwd
					+ "'";
			System.out.println(query);
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				System.out.println("Name=" + rs.getString("name") + ",country=" + rs.getString("country") + ",password="
						+ rs.getString("password"));
			}
		} finally {
			if (rs != null)
				rs.close();
			stmt.close();
			con.close();
		}

	}

}

// Now let’s see how a hacker can get unauthorized access 
// to a user because we are using Statement for executing queries.


//Please enter email id:
//david@gmail.com' or '1'='1
//User id=david@gmail.com' or '1'='1
//Please enter password to get details:
//
//User password=
//DB Connection created successfully
//select name, country, password from Users where email = 'david@gmail.com' or '1'='1' and password=''
//Name=David,country=USA,password=david123

// 可以看到，即使没有密码，我们也能够获取用户详细信息。 这里要注意的关键点是查询是通过字符串连接创建的

// 这是SQL注入的一个示例，其中糟糕的编程负责使我们的应用程序容易受到未经授权的数据库访问。

//这就是为什么JDBC API提出了PreparedStatement接口，它扩展了Statement并在执行查询之前自动转义特殊字符。
