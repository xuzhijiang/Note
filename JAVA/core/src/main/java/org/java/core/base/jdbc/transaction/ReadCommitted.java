package org.java.core.base.jdbc.transaction;

import org.java.core.base.jdbc.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 不可重复读演示以及解决
public class ReadCommitted {

    private static String SQL_CONFIG_FILE_NAME = "/jdbc.properties";

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 插入线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = JDBCUtils.getConnection();
                    conn.setAutoCommit(false);
                    synchronized (lock) {
                        lock.wait();
                    }
                    // 插入数据
                    System.out.println(Thread.currentThread().getName() + " - 插入数据");
                    PreparedStatement prepare = conn.prepareStatement("insert into person (id,name,country) values(?,?,?)");
                    prepare.setInt(1, 1);
                    prepare.setString(2, "xzj2");
                    prepare.setString(3, "japan");
                    prepare.executeUpdate();
                    conn.commit();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(conn);
                }
            }
        }, "事务A-插入线程");

        // 查询线程
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = JDBCUtils.getConnection();
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    // 如果自动commit是true,那么2次select就是2个不同的事务中的,所以即使隔离级别设置为Connection.TRANSACTION_REPEATABLE_READ,
                    // 依然可以看到不可重复读现象
                    // 所以下面的2次select需要在同一事务里面,就需要我们关闭自动提交
                    conn.setAutoCommit(false);
                    // 第一次读取
                    select("xzj2", conn, "第一次读取");
                    synchronized (lock) {
                        lock.notify();
                    }
                    Thread.sleep(1000);
                    // 第二次读取
                    select("xzj2", conn, "第二次读取");
                    conn.commit();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(conn);
                }
            }
        }, "事务B-查询线程");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static void select(String name, Connection conn, String extra) {
        System.out.println(Thread.currentThread().getName() + " - " + extra);
        PreparedStatement prepare = null;
        try {
            prepare = conn.prepareStatement("select * from person where name = ?");
            prepare.setString(1, name);
            ResultSet resultSet = prepare.executeQuery();
            while (resultSet.next()) {
                System.out.println(Thread.currentThread().getName() + " - name: " + resultSet.getString("name")
                + ", country: " + resultSet.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
