package org.java.core.base.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DBConnection {
	
	public static Connection getConnection() {
		Connection con = null;
		Properties properties = new Properties();
		InputStream is;
		try {
			
			is = new FileInputStream("db-resource.properties");
			properties.load(is);
			
			// load the Driver Class
			Class.forName(properties.getProperty("DB_DRIVER_CLASS"));

			// create the connection now
			con = DriverManager.getConnection(properties.getProperty("DB_URL"), 
					properties.getProperty("DB_USERNAME"), 
					properties.getProperty("DB_PASSWORD"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
}
