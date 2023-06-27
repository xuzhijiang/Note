package com.mybatis.spring.transaction.service;

import com.mybatis.spring.transaction.dao.TbContentCategoryDao;
import com.mybatis.spring.transaction.domain.TbContent;
import com.mybatis.spring.transaction.domain.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbContentCategoryService {

    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;

    public void insert(TbContentCategory tbContentCategory) {
        // 可以在这里加断点,看看执行完这个后,数据库是否有内容.
        tbContentCategoryDao.insert(tbContentCategory);
    }
}