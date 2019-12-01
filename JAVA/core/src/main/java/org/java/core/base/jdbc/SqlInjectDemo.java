package org.java.core.base.jdbc;

import org.java.core.base.jdbc.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 这是一个典型的SQL注入案例.
// 以下是一个只知道用户的email，不知道用户的密码信息，竟然可以查出来用户的全部信息.
public class SqlInjectDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String email = "xxx@qq.com";
        String password = "'a' or 'a' = 'a'";

        // 因为没有将特殊字符'转义，所以可以利用sql的语法规则，跳过密码的验证.
        String sql = "select id, username, password, email from tb_user where email = '" + email + "' and password = " + password;
        System.out.println(sql);
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Name=" + rs.getString("username")
                        + ",email=" + rs.getString("email")
                        + ",pwd=" + rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, connection);
        }
    }

}
