package org.java.core.base.jdbc.PreparedStatementINclausealternatives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPreparedStatementSingle {

	private static final String QUERY = "select empid, name from Employee where empid = ?";
	
	public static void printData(int[] ids){
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(QUERY);
			
			// 执行多次的executeQuery查询,查询10条记录，就执行10次
			
			// 方法很简单，但速度很慢，因为如果有100个参数，那么它将进行100次数据库调用。 
			// 这将导致100个ResultSet对象，这些对象将使系统过载，并且还会导致性能下降。 所以不推荐这种方法。
			for(int empid : ids){
				ps.setInt(1, empid);
				rs = ps.executeQuery();
				
				while(rs.next()){
					System.out.println("Employee ID="+rs.getInt("empid")+", Name="+rs.getString("name"));
				}
				
				//close the resultset here
				try{
					rs.close();
				} catch(SQLException e){}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
