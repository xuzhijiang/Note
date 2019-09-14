package com.hello.spring.transaction.aspectsj.aop.service;

import com.hello.spring.transaction.aspectsj.aop.domain.TbContent;
import com.hello.spring.transaction.aspectsj.aop.domain.TbContentCategory;

public interface TbContentCategoryService {
    void save(TbContentCategory tbContentCategory, TbContent tbContent);
}
