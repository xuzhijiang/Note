package com.hello.spring.transaction.aspectsj.aop.dao;

import com.hello.spring.transaction.aspectsj.aop.domain.TbContentCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentCategoryDao {
    void insert(TbContentCategory tbContentCategory);
}