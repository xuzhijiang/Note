package org.java.core.base.jdbc.mycat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyCatConnectTest {
    public static void main(String[] args) throws Exception {
        // 1. 加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2. 获得连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.32.128:8066/TESTDB",
                "root", "123456");
        System.out.println(connection);

        String sql = "select * from tb_items order by id desc";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            System.out.println("id: " + resultSet.getInt("id")
            + ", title: " + resultSet.getString(2) +
                    ", price: " + resultSet.getDouble("price"));
        }

        resultSet.close();
        ps.close();
        connection.close();
    }
}
