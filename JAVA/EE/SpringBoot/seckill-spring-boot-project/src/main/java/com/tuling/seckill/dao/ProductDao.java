package com.tuling.seckill.dao;

import com.tuling.seckill.model.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    @Select("select * from product")
    List<Product> listProducts();

    @Select("select * from product where id=#{id}")
    Product getProductById(Long id);

    @Insert("insert into product(id, name, price, stock, pic) values(#{id}, #{name}, #{price}, #{stock}, #{pic})")
    void saveProduct(Product product);

    @Update("update product set name=#{name},price=#{price},stock=#{stock},pic=#{pic} where id=#{id}")
    void updateProduct(Product product);

    // 用数据库的乐观锁来实现超卖的控制, 数据库会给这一行记录做一个行锁,行锁的意思就是你多线程来执行这一行语句
    // mysql会帮你把线程排队,不会并发执行,这样就不会超卖
    @Update("update product set stock=stock-1 where id=#{id} and stock>0")
    int deductProductStock(Long id);

    @Delete("delete from product where id=#{id}")
    void deleteProduct(Long id);
}
