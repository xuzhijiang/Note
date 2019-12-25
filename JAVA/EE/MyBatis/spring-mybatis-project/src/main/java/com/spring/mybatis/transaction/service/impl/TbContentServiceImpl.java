package com.spring.mybatis.transaction.service.impl;

import com.spring.mybatis.transaction.dao.TbContentDao;
import com.spring.mybatis.transaction.domain.TbContent;
import com.spring.mybatis.transaction.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentDao tbContentDao;

    public void save(TbContent tbContent) {
        tbContentDao.insert(tbContent);
    }
}