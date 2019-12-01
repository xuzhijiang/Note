package org.java.core.base.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DruidPoolParamTest {

    @Test
    public void testMaxPoolSize() throws SQLException {
        for (int i = 0; i < 10; i++) {
            Connection connection = DruidUtils.getConnection();
            System.out.println("第" + (i+1) + "个连接Connection: " + connection);
        }
    }

    @Test
    public void testMaxWait() throws SQLException {
        // 获取第11个Connection会报错,因为Connection不够用了,之前的10个Connection都没有归还
        for (int i = 0; i < 11; i++) {
            Connection connection = DruidUtils.getConnection();
            System.out.println("第" + (i+1) + "个连接Connection: " + connection);
        }
    }

    @Test
    public void testClose() throws SQLException {
        for (int i = 0; i < 11; i++) {
            Connection connection = DruidUtils.getConnection();
            if (i == 5) {
                // 归还到连接池 中
                connection.close();
            }
            System.out.println("第" + (i+1) + "个连接Connection: " + connection);
        }
    }
}
