package com.tuling.seckill.dao;

import com.tuling.seckill.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {

    @Insert("insert into seckillorder(productId, amount) values(#{productId}, #{amount})")
    void saveOrder(Order order);

    @Select("select * from seckillorder where id=#{id}")
    Order getOrderById(Long id);

    @Update("update seckillorder set productId=#{productId}, amount=#{amount} where id=#{id}")
    void updateOrder(Order order);

    @Delete("delete from seckillorder where id=#{id}")
    void deleteOrder(Long id);
}
