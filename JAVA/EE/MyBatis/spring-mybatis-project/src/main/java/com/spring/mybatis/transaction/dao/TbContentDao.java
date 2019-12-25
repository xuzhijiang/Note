package com.spring.mybatis.transaction.dao;

import com.spring.mybatis.transaction.domain.TbContent;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentDao {
    void insert(TbContent tbContent);
}