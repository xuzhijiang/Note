package com.spring.mybatis.transaction.service;

import com.spring.mybatis.transaction.domain.TbContent;
import com.spring.mybatis.transaction.domain.TbContentCategory;

public interface TbContentCategoryService {
    void save(TbContentCategory tbContentCategory, TbContent tbContent);
}
