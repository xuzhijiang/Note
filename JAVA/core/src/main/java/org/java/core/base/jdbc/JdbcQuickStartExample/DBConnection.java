package org.java.core.base.jdbc.JdbcQuickStartExample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
	
	public static Connection getConnection(String name) {
		Connection connection = null;
		Properties props = new Properties();
		InputStream inputStream = null;
		try {
			// 加载classpath下的name
			inputStream = Object.class.getResourceAsStream(name);
			props.load(inputStream);

			//load the Driver Class
			// 重要代码是Class.forName（）方法调用
			// 使用反射来创建实例(Class的实例)有助于我们编写松耦合的代码，
			// 如果我们使用new运算符则无法实现松耦合,在这种情况下，如果不进行相应的代码更改，
			// 我们无法切换到不同的数据库。

			// 此步的动机是将类加载到内存中，以便驱动程序类可以将自身注册到DriverManager。
			// 如果您将查看Driver类的实现，您会发现它们具有静态代码块(static静态代码块在类加载到内存的时候就会执行)，
			// 静态代码块中，将自己注册到DriverManager。
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));

			// 创建数据库连接：
			// DriverManager.getConnection()方法使用已经注册的JDBC驱动去创建
			// 数据库连接，如果在获取数据库的过程中遇到了任何问题，
			// 此方法都会抛出java.sql.SQLException
			connection = DriverManager.getConnection(props.getProperty("DB_URL"),
					props.getProperty("DB_USERNAME"),
					props.getProperty("DB_PASSWORD"));
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
