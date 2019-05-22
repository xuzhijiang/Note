package org.java.core.base.jdbc.BatchProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class JDBCBatchExceptions2 {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement ps = null;
		String query = "insert into Employee (empId, name) values (?,?)";
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);

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

			int[] results = ps.executeBatch();

			con.commit();
			System.out.println(Arrays.toString(results));

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 正如您所看到的，如果出现任何SQL异常，我将回滚事务。 如果批处理成功，我显式提交事务。
			// 这就是JDBC批量插入更新示例的所有内容，请确保试验您的数据以获得批量查询的批量大小的最佳值。

			// jdbc批处理的一个限制是我们不能在批处理中执行不同类型的查询。
			// One of the limitation of jdbc batch processing 
			// is that we can’t execute different type of queries in the batch.
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
