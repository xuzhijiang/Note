package com.mybatis.spring.transaction.dao;

import com.mybatis.spring.transaction.domain.TbContent;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentDao {
    void insert(TbContent tbContent);
}