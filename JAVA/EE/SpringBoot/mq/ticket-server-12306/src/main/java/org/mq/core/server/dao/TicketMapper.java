package org.mq.core.server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mq.core.server.domain.ZgTicket;

import java.util.List;

@Mapper
public interface TicketMapper {
    // 插入api
    @Insert("insert into zg_ticket(ticketMoney, ticketStatus, ticketTime, version) values(#{ticketMoney}, #{ticketStatus}, #{ticketTime}, #{version})")
    int addTicket(ZgTicket zgTicket);

    // 带状态的查询api定义
    @Select("select * from zg_ticket where ticketId = #{id} and version = 0")
    ZgTicket queryTicketById(String id);

    // 更新version, version=1表示该票已经被消费
    @Update("update zg_ticket set version = #{version} + 1 where ticketId = #{ticketId} and version = #{version}")
    int updateZgTicketVersion(ZgTicket zgTicket);

    // 这里查询的version=0的票,version=0说明这个票还没有被购买
    @Select("select * from zg_ticket where ticketStatus=0 and version=0")
    List<ZgTicket> queryAllTicket();

    // 抢票成功后,改变票的属性
    @Update("update zg_ticket set ticketStatus=1 where ticketId=#{ticketId} and version = 1")
    int updateZgTicketState(ZgTicket zgTicket);
}
