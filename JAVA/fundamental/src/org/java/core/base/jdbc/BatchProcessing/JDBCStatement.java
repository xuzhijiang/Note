package org.java.core.base.jdbc.BatchProcessing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCStatement {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			
			long start = System.currentTimeMillis();
			for(int i =0; i<10000;i++){
				String query = "insert into Employee values ("+i+",'Name"+i+"')";
				stmt.execute(query);
			}
			System.out.println("Time Taken="+(System.currentTimeMillis()-start));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
