package com.spring.mybatis.transaction;

import com.spring.mybatis.transaction.domain.TbContent;
import com.spring.mybatis.transaction.domain.TbContentCategory;
import com.spring.mybatis.transaction.service.TbContentCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运行观察事务效果：
 *
 * 有事务：数据插入成功则两张表都存在数据，只要出现异常则两张表都没有数据
 * 无事务：如果第一张表数据插入成功，但第二张表报错则第一张表的数据不会回滚
 */
// 因为要加载xml,所以使用的是spring的SpringJUnit4ClassRunner套件
@RunWith(SpringJUnit4ClassRunner.class)
// 上下文配置
@ContextConfiguration({"classpath:config/transaction/spring-context.xml", "classpath:config/transaction/spring-context-datasource.xml", "classpath:config/transaction/spring-context-mybatis.xml"})
// 如果是测试,就添加下面2个注解,因为只是测试,不往数据库中添加数据.
@Transactional
@Rollback
public class TestSpringTransaction {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @Test
    public void test() {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(1L);
        tbContentCategory.setName("测试事务分类");

        TbContent tbContent = new TbContent();
        tbContent.setTbContentCategory(tbContentCategory);
        // 在这里你可以将内容设置为超出数据库字段的存储范围来验证事务是否开启
        tbContent.setTitle("测试事务内容");

        tbContentCategoryService.save(tbContentCategory, tbContent);
    }

}