package com.mybatis.spring.transaction.service;

import com.mybatis.spring.transaction.dao.TbContentDao;
import com.mybatis.spring.transaction.domain.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TbContentService {

    @Autowired
    private TbContentDao tbContentDao;

    public void insert(TbContent tbContent) {
        tbContentDao.insert(tbContent);
    }
}