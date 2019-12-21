package com.springboot.customAop.manager;

import com.springboot.customAop.util.ConnectionUtils;

import java.sql.SQLException;

/**
 * 和事务管理相关的功能类，它包含了，开启事务，提交事务，回滚事务和释放连接
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public TransactionManager(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 开启事务
     */
    public void beginTransaction() {
        try {
            System.out.println("开启事务");
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void commit() {
        try {
            connectionUtils.getThreadConnection().commit();
            System.out.println("提交事务");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            connectionUtils.getThreadConnection().rollback();
            System.out.println("回滚事务");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接
     */
    public void release() {
        try {
            //还回连接池中
            connectionUtils.getThreadConnection().close();
            connectionUtils.removeConnection();
            System.out.println("释放连接");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
