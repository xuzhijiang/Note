package org.java.core.base.jdbc.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	
	public static Connection getConnection() {
		Connection con = null;
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			// db.properties指的是项目根目录下的db.properties,
			// 也就是和src同级目录下的db.properties，
			// 而不是和当前Java文件同级目录下的db.properties
			fis = new FileInputStream("db.properties");
			props.load(fis);

			//load the Driver Class
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));
			// 需要注意的重要代码是Class.forName（）方法调用
			// 使用反射来创建实例(Class的实例)有助于我们编写松散耦合的代码，
			// 如果我们使用new运算符则无法实现这些代码,在这种情况下，如果不进行相应的代码更改，我们无法切换到不同的数据库。
			
			// 主要动机是将类加载到内存中，以便驱动程序类可以将自身注册到DriverManager。
			// 如果您将查看Driver类的实现，您会发现它们具有静态块(static块在加载到内存的时候就会执行)，
			// 它们将自己注册到DriverManager。
			
			
			//create the connection now
			con = DriverManager.getConnection(props.getProperty("DB_URL"),
					props.getProperty("DB_USERNAME"),
					props.getProperty("DB_PASSWORD"));
			//DriverManager.getConnection() method uses the registered 
			//JDBC drivers to create the database connection. This method 
			//throws java.sql.SQLException if there is any problem in getting the database connection.
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}

// 此代码仅使用Java JDBC API类，并且无法知道它是否连接到哪种类型的数据库

//oracle.jdbc.driver.OracleDriver.java snippet:
//static
//  {
//    try
//    {
//      if (defaultDriver == null)
//      {
//        defaultDriver = new oracle.jdbc.OracleDriver();
//        DriverManager.registerDriver(defaultDriver);
//      }
//	//some code omitted for clarity
//	}
//}


//com.mysql.jdbc.Driver.java snippet:
//
//static
//  {
//    try
//    {
//      DriverManager.registerDriver(new Driver());
//    } catch (SQLException E) {
//      throw new RuntimeException("Can't register driver!");
//    }
//  }

// This is a great example where we are making our code 
// loosely-coupled with the use of reflection API. So basically 
// we are doing following things using Class.forName() method call.


//Driver driver = new OracleDriver();
//DriverManager.registerDriver(driver);