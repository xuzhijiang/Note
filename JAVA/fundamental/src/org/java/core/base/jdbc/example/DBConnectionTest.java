package org.java.core.base.jdbc.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionTest {

	public static final String QUERY = "select id,name,email,country,password from Users";
	
	public static void main(String[] args) {
		
		//using try-with-resources to avoid closing resources (boiler plate code样板代码)
		try(Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(QUERY)){
			// Connection.createStatement() is used to create the Statement object and then executeQuery() method is used to run the query and get the result set object.
			
			//First call to ResultSet next() method call moves the cursor 
//			to the first row and subsequent calls moves the cursor to next 
//			rows in the result set. If there are no more rows then it returns 
//			false and come out of the while loop. We are using result set 
//			getXXX() method to get the columns value and then writing them to the console.
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				String password = rs.getString("password");
				System.out.println(id + "," + name + "," + email + "," + country + "," + password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Notice that we are using Java 7 try-with-resources feature to make sure that resources are closed as soon as we are out of try-catch block.
		
//		JDBC Connection，Statement和ResultSet是昂贵的资源，我们应该在完成使用后立即关闭它们。
	}
}
