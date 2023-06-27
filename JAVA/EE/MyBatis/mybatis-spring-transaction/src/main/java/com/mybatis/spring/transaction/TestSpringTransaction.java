package com.mybatis.spring.transaction;

import com.mybatis.spring.transaction.domain.TbContent;
import com.mybatis.spring.transaction.domain.TbContentCategory;
import com.mybatis.spring.transaction.service.TbContentCategoryService;
import com.mybatis.spring.transaction.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

// 因为要加载spring的xml配置文件,所以使用的是spring的SpringJUnit4ClassRunner套件
@RunWith(SpringJUnit4ClassRunner.class)
// 上下文配置
@ContextConfiguration({"classpath:spring-context.xml"})
// 如果是测试,就添加下面2个注解,因为只是测试,不往数据库中添加数据.
//@Transactional
//@Rollback
public class TestSpringTransaction {

    @Autowired
    private TestService testService;

    @Test
    public void test() throws Exception {
        TbContentCategory tbContentCategory = new TbContentCategory();
        TbContentCategory parent = new TbContentCategory();
        parent.setId(2L);
        tbContentCategory.setParent(parent);
        tbContentCategory.setName("悬疑");
        tbContentCategory.setSortOrder(3);

        TbContent tbContent = new TbContent();
        tbContent.setTbContentCategory(tbContentCategory);
        tbContent.setTitle("七宗罪");
        tbContent.setSubTitle("哪7种罪");
        tbContent.setTitleDesc("7宗罪-desc");

        testService.save(tbContentCategory, tbContent);
    }

}