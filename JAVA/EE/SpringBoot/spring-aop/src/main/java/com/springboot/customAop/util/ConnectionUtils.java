package com.springboot.customAop.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 */
public class ConnectionUtils {

    /**
     * 事务操作，必须使用同一个Connection对象。如何保证？
     * 第一次从数据源获取Connection对象并开启事务后，将它存入当前线程的ThreadLocal中，
     * 等到了DAO层，还是从ThreadLocal中取，这样就能保证开启事务和操作数据库使用的Connection对象是同一个。
     */
    private ThreadLocal<Connection> t1 = new ThreadLocal<>();

    private static BasicDataSource dataSource = new BasicDataSource();

    //静态代码块,设置连接数据库的参数
    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
    }

    /**
     * 获取当前线程上的连接
     */
    public Connection getThreadConnection() {
        try {
            //1.先从ThreadLocal上获取
            Connection conn = t1.get();
            //2.判断当前线程上是否有连接
            if (conn == null) {
                //3.从数据源中获取一个连接，并且存入ThreadLocal中
                conn = dataSource.getConnection();
                t1.set(conn);
            }
            //4.返回当前线程上的连接
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection() {
        t1.remove();
    }

}
