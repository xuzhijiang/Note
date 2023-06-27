package com.mybatis.spring.transaction.service;

import com.mybatis.spring.transaction.domain.TbContent;
import com.mybatis.spring.transaction.domain.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TbContentCategoryService tbContentCategoryService;
    @Autowired
    TbContentService tbContentService;

    /**
     * 运行观察事务效果：
     *
     * 有事务：数据插入成功则两张表都存在数据，只要出现异常则两张表都没有数据
     * 无事务：如果第一张表数据插入成功，但第二张表报错则第一张表的数据不会回滚
     */
    // 一旦整个save方法被控制在事务内,在整个方法没有执行完成之前,数据库内容是没有变化的.
    public void save(TbContentCategory tbContentCategory, TbContent tbContent) throws Exception {
        // 操作2张表
        // 第一张表: tb_content_category
        tbContentCategoryService.insert(tbContentCategory);

        if (true) {
            throw new Exception();
        }

        // 第二张表: tb_content
        tbContentService.insert(tbContent);
    }
}
