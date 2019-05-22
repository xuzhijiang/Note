package org.java.core.base.jdbc.StmtAndPrepStmt;

import org.java.core.base.jdbc.JdbcQuickStartExample.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 以下是一个只知道用户的email，不知道用户的密码信息，竟然可以查出来用户的全部信息.
// 也就是，即使没有密码，我们也能够获取用户详细信息。 这里要注意的关键点是查询是通过字符串拼接创建的

// 这是一个典型的SQL注入案例.

// 这就是为什么JDBC API提出了PreparedStatement接口，它扩展了Statement并在执行查询之前自动转义特殊字符:

public class FlawOfStatementExample {// Statement的缺陷弊端

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String email = "xzj@qq.com' or '1'='1";// 只知道用户的email
        String pwd = "";// 不用输入用户密码
        // 因为没有将特殊字符转义，所以可以利用sql的语法规则，跳过密码的验证.
        String query = "select id, name, age, email, pwd from user where email = '" + email + "' and pwd='" + pwd + "'";
        System.out.println(query);
        try (Connection connection = DBConnection.getConnection("/db-resource.properties");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Name=" + rs.getString("name")
                        + ",email=" + rs.getString("email")
                        + ",pwd=" + rs.getString("pwd"));
            }

        }

    }

}
