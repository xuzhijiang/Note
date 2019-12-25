package com.spring.mybatis.transaction.dao;

import com.spring.mybatis.transaction.domain.TbContentCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentCategoryDao {
    void insert(TbContentCategory tbContentCategory);
}