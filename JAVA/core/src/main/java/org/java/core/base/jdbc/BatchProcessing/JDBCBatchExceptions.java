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

			String name1 = "xuzhijiang";
			String name2 = "xuzhijiang xuzhijiang"; // longer than column length
			String name3 = "xuzhijiang";

			ps.setInt(1, 1);
			ps.setString(2, name1);
			ps.addBatch();

			ps.setInt(1, 2);
			ps.setString(2, name2);
			ps.addBatch();

			ps.setInt(1, 3);
			ps.setString(2, name3);
			ps.addBatch();

			// 但是异常之前的行已成功插入数据库。
			// 虽然Exception清楚地说明了错误是什么，但它没有告诉我们哪个查询导致了问题。
			// 因此，我们要么在将数据添加到批处理之前验证数据，要么我们应该使用
			// JDBC事务管理（ JDBC Transaction Management ）
			// 来确保所有记录或没有记录被插入而不是例外。
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
