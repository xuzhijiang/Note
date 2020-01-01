package org.java.core.advanced.DesignPatterns.creational.simpleFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 简单工厂模式在JDK中最典型的应用就是JDBC了,可以把关系型数据库认为是一种抽象产品，
 * 各具体关系型数据库（MySQL，PostgreSQL，Oracle）则是具体产品。DriverManager是工厂类。
 * 使用关系型数据库时，并不需要关心具体使用的是哪种数据库，
 * 而直接使用DriverManager的静态方法去得到该数据库的Connection，
 * 具体是哪个数据库由Class.forName加载数据库驱动类决定。
 */
public class JDKSimpleFactory {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb");
        } catch (Exception e) {

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
