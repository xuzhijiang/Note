package org.java.core.base.jdbc.transaction;

import org.java.core.base.jdbc.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadUncommittedTest {

    private static String SQL_CONFIG_FILE_NAME = "/jdbc.properties";

    /**
     * 测试读未提交(脏读)
     */
    @Test
    public void testReadUncommitted() throws InterruptedException {
        // 第一个线程(插入线程),事务A-插入一条数据
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = JDBCUtils.getConnection();
                    // 设置自动提交为false
                    conn.setAutoCommit(false);
                    // 测试之前要把person最好清空了
                    PreparedStatement prepare = conn.prepareStatement("INSERT INTO person (NAME, country) VALUES(?,?)");
                    prepare.setString(1, "xzj1");
                    prepare.setString(2, "india");
                    prepare.executeUpdate();
                    System.out.println(Thread.currentThread().getName() + " - 执行插入");
                    Thread.sleep(3000);
                    // 注意这里并没有手动提交数据
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "事务A-insert");
        t1.start();

        // 休眠1秒
        Thread.sleep(1000);

        // 启用查询线程,事务2-查询数据
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = JDBCUtils.getConnection();
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    PreparedStatement prepare = conn.prepareStatement("select * from person where name = 'xzj1'");
                    ResultSet resultSet = prepare.executeQuery();
                    // 发现数据库里面并没有xzj1的数据,但是却读到了
                    while (resultSet.next()) {
                        System.out.println("name: " + resultSet.getString("name") +
                                ", country: " + resultSet.getString("country"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, "事务B-select");
        t2.start();

        t1.join();
        t2.join();
    }
}
