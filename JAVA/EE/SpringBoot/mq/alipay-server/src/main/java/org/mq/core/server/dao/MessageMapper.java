package org.mq.core.server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mq.core.server.domain.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Update("update zg_message set state = #{state} where message_id = #{message_id}")
    int updateMessage(Message message);

    @Insert("insert into zg_message (user_id, message_id, amount, state) values (#{user_id}, #{message_id}, #{amount}, 'unconfirm')")
    int addMessage(Message message);

    @Select("select * from zg_message where state = #{state}")
    List<Message> queryMessageByState(String state);
}
