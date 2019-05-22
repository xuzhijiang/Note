package org.java.core.base.jdbc.PreparedStatementINclausealternatives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPreparedStatementDynamic {

	public static void printData(int[] ids){
		
		String query = createQuery(ids.length);
		
		System.out.println("Query="+query);
		
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(query);
			
			for(int i = 1; i <=ids.length; i++){
				ps.setInt(i, ids[i-1]);
			}
			rs = ps.executeQuery();
				
			while(rs.next()){
				System.out.println("Employee ID="+rs.getInt("empid")+", Name="+rs.getString("name"));
			}
				
			//close the resultset here
			try{
				rs.close();
			} catch(SQLException e){}
			
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

	// 此方法涉及编写逻辑以根据IN子句中元素的大小动态创建PreparedStatement查询。
	private static String createQuery(int length) {
		String query = "select empid, name from Employee where empid in (";
		StringBuilder queryBuilder = new StringBuilder(query);
		for( int i = 0; i< length; i++){
			if(i == 0) {
				queryBuilder.append("?");
			}else {
				queryBuilder.append(" ?");
			}
			// 如果是最后一个，则不拼接,
			if(i != length -1) queryBuilder.append(",");
		}
		queryBuilder.append(")");
		return queryBuilder.toString();
	}
}
