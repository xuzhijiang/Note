package com.spring.tx.core.distributed;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// 原生的JDBC的XA的实现
public class XATest {

    public static void main(String[] args) throws Exception {
        // true表示打印XA语句,用于调试
        boolean logXaCommands = true;

        // 获得资源管理器操作接口实例 RM1
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_user", "root", "password");
        XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn1, logXaCommands);
        // 获取xaConn1对应的资源管理器,这个资源管理器有一些api
        XAResource rm1 = xaConn1.getXAResource();

        // 获得资源管理器操作接口实例 RM2
        Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_account", "root", "password");
        XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn2, logXaCommands);
        XAResource rm2 = xaConn2.getXAResource();

        // AP请求TM执行一个分布式事务,TM生成全局事务id
        byte[] gtrid = "g12345".getBytes();// gtrid: global transaction id
        int formatId = 1;

        try {
            // =============分别执行RM1和RM2上的事务分支=================

            // TM生成rm1上的事务分支id
            byte[] bqual1 = "b00001".getBytes();
            MysqlXid xid1 = new MysqlXid(gtrid, bqual1, formatId);
            // 执行rm1上的事务分支
            rm1.start(xid1, XAResource.TMNOFLAGS); // One of TMNOFLAGS, TMJOIN
            PreparedStatement ps1 = conn1.prepareStatement("insert into user(name) values('tianshouzhi')");
            ps1.execute();
            // 打一个end的标签,做一个标记,意思是这条sql语句执行完了,但是还没有commit
            rm1.end(xid1, XAResource.TMSUCCESS);

            // TM生成rm2上的事务分支id
            byte[] bqual2 = "b00002".getBytes();
            MysqlXid xid2 = new MysqlXid(gtrid, bqual2, formatId);
            // 执行rm2上的事务分支
            rm2.start(xid2, XAResource.TMNOFLAGS);
            PreparedStatement ps2 = conn2.prepareStatement("insert into account(name) values('wangxiaoxiao')");
            ps2.execute();
            rm2.end(xid2, XAResource.TMSUCCESS);

            // ================================两阶段提交==========================
            // phase1(第一阶段): 询问所有的RM 准备提交事务分支
            int rm1_prepare = rm1.prepare(xid1); // 上面的sql执行完成后,进行预提交,返回值是预提交的结果.
            int rm2_prepare = rm2.prepare(xid2);
            // phase2(第二阶段): 提交所有事务分支
            boolean onePhase = false; // TM判断有2个事务分支,所以不能优化为一阶段提交
            // 判断第一阶段预提交的结果,来决定第二阶段如何进行.
            if (rm1_prepare == XAResource.XA_OK && rm2_prepare == XAResource.XA_OK) { // 所有分支都prepare成功,则进行提交
                rm1.commit(xid1, onePhase);
                rm2.commit(xid2, onePhase);
            } else { // 如果有事务分支没有成功,则回滚.
                rm1.rollback(xid1);
                rm2.rollback(xid2);
            }
        } catch (XAException e) {
            // 如果出现异常,也要进行回滚
        }
    }


}
