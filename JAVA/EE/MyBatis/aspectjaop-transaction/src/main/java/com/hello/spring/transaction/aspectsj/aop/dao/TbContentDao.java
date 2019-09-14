package com.hello.spring.transaction.aspectsj.aop.dao;

import com.hello.spring.transaction.aspectsj.aop.domain.TbContent;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentDao {
    void insert(TbContent tbContent);
}