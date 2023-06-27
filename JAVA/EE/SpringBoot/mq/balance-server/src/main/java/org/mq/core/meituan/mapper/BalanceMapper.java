package org.mq.core.meituan.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BalanceMapper {

    @Select("select count(*) from zg_message where message_id=#{message_id}")
    int selectMessageCountByMessageId(String message_id);

    @Update("update zg_account set amount=amount+#{amount} where user_id=#{user_id}")
    void updateAmount(Integer amount, String user_id);

    @Insert("insert into zg_message (message_id, user_id, amount) values(#{message_id}, #{user_id}, #{amount})")
    void addMessage(String user_id, String message_id, Integer amount);
}
