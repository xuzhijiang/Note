package com.mybatis.spring.transaction.dao;

import com.mybatis.spring.transaction.domain.TbContentCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentCategoryDao {
    void insert(TbContentCategory tbContentCategory);
}