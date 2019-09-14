package com.hello.spring.transaction.aspectsj.aop.service.impl;

import com.hello.spring.transaction.aspectsj.aop.dao.TbContentCategoryDao;
import com.hello.spring.transaction.aspectsj.aop.domain.TbContent;
import com.hello.spring.transaction.aspectsj.aop.domain.TbContentCategory;
import com.hello.spring.transaction.aspectsj.aop.service.TbContentCategoryService;
import com.hello.spring.transaction.aspectsj.aop.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;

    @Autowired
    private TbContentService tbContentService;

    // 操作2张表
    public void save(TbContentCategory tbContentCategory, TbContent tbContent) {
        // 可以在这里加断电,看看执行完这个后,数据库是否有内容.
        // 一旦整个save方法被控制在事务内,在整个方法没有执行完成之前,数据库是没有内容变化的.
        tbContentCategoryDao.insert(tbContentCategory);

        // 如果不添加事务,那么上面的insert可以成功,下面的save就失败了,因为抛异常了,
        // 所以要添加事务.保证整个方法都成功.
//        if (true) {
//            throw new Exception();
//        }

        tbContentService.save(tbContent);
    }
}