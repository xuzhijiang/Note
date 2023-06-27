package com.mybatis.core.MultipleDataSource;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class AccountDao extends SqlSessionDaoSupport{

    private static String NAMESPACE = "com.mybatis.core.MultipleDataSource.AccountMapper";

    public Account selectById(int id) {
        Account account = getSqlSession().selectOne(NAMESPACE + ".selectById", id);
        return account;
    }
}
