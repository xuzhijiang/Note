package com.spring.tx.core.distributed;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.transaction.SystemException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AtomikosJTATest {

    // atomikos也是有spring的整合,这里使用的是atomikos的原生api
    private static AtomikosDataSourceBean createAtomikosDataSourceBean(String db_name) {
        // 连接池基本属性
        Properties p = new Properties();
        p.setProperty("url", "jdbc:mysql://localhost:3306/" + db_name);
        p.setProperty("user", "root");
        p.setProperty("password", "password");

        // 使用AtomikosDataSourceBean封装com.mysql.jdbc.jdbc2.optional.MysqlXADataSource
        // 把数据源交给Atomikos做一个代理.
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        // atomikos要求为每个AtomikosDataSourceBean命名,为了方便记忆,这里设置为和dbName相同
        ds.setUniqueResourceName(db_name);
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setXaProperties(p);

        return ds;
    }

    public static void main(String[] args) {
        // 把数据源封装成atomikos,也就是对数据库的操作由atomikos来代理
        AtomikosDataSourceBean ds1 = createAtomikosDataSourceBean("db_user");
        AtomikosDataSourceBean ds2 = createAtomikosDataSourceBean("db_account");

        Connection conn1 = null;
        Connection conn2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        // 开启一个全局事务
        UserTransactionImp userTransaction = new UserTransactionImp();
        try {
            // 开启事务
            userTransaction.begin();

            // 执行db1上的sql
            conn1 = ds1.getConnection();
            ps1 = conn1.prepareStatement("insert into user(name) values(?)", Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, "zhugelaoshi");
            // 这里只是执行,并没有commit.
            ps1.executeUpdate();

            // 模拟异常,直接进入catch代码块,2个都不会提交.
            // int i = 1/0;

            // 执行ds2上的sql
            conn2 = ds2.getConnection();
            ps2 = conn2.prepareStatement("insert into account(name) values(?)");
            ps2.setString(1, "zhugelaoshi666");
            ps2.executeUpdate();

            // 跨系统调用接口

            // 两阶段提交(全局事务的提交)
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // 回滚
                userTransaction.rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                ps1.close();
                ps2.close();
                conn1.close();
                conn2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
