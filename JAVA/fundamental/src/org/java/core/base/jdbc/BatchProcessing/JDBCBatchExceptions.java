package org.java.core.base.jdbc.BatchProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class JDBCBatchExceptions {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement ps = null;
		String query = "insert into Employee (empId, name) values (?,?)";
		try {
			con = DBConnection.getConnection();

			ps = con.prepareStatement(query);

			String name1 = "Pankaj";
			String name2 = "Pankaj Kumar"; // longer than column length
			String name3 = "Kumar";

			ps.setInt(1, 1);
			ps.setString(2, name1);
			ps.addBatch();

			ps.setInt(1, 2);
			ps.setString(2, name2);
			ps.addBatch();

			ps.setInt(1, 3);
			ps.setString(2, name3);
			ps.addBatch();

			int[] results = ps.executeBatch();

			System.out.println(Arrays.toString(results));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
