package com.hello.spring.transaction.aspectsj.aop.service.impl;

import com.hello.spring.transaction.aspectsj.aop.dao.TbContentDao;
import com.hello.spring.transaction.aspectsj.aop.domain.TbContent;
import com.hello.spring.transaction.aspectsj.aop.service.TbContentService;
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