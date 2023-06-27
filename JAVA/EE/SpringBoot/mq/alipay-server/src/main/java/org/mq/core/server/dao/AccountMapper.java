package org.mq.core.server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.mq.core.server.domain.Account;

@Mapper
public interface AccountMapper {
    @Update("update zg_account set amount = amount-#{amount}, update_time = now() where user_id=#{userId}")
    int updateAccount(Account account);

    @Insert("insert zg_account (user_id, amount, update_time) values (#{userId}, #{amount}, now())")
    int addAccount(Account account);
}
