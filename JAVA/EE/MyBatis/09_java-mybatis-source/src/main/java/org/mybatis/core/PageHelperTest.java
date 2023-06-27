package org.mybatis.core;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * PageHelper测试,注意要关闭自定义插件,否则可能影响PageHelper的测试
 *
 * PageHelper支持各种主流数据库的分页(mysql,oracle...)
 */
public class PageHelperTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    // 第1种获取分页详细信息的方法:
    @Test
    public void test01() throws IOException {
        // 需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可，
        // 紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页
        SqlSession openSession = getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = openSession.getMapper(UserMapper.class);

            // 只需要调用PageHelper.startPage(2, 10);就可以分页了.
            // 原理:
            // 1. 将pageNum和pageSize封装为com.github.pagehelper.Page对象，保存在ThreadLocal中，实现线程间数据隔离。
            // 2. Pagehelper实现了Mybatis的Interceptor接口，调用拦截StatementHandler（Sql语法的构建处理）方法，按照物理库的不同重构SQL实现分页


            Page<Object> page = PageHelper.startPage(2, 4);// 获取第2页, 每页4条内容，默认会查询总数count
            // 紧跟着的第一个select方法会被分页 (官网注释)
            // 需要注意的是，分页代码PageHelper.startPage(pageNo,pageSize);只对其后的第一个查询有效

            // 执行sql之前,重写sql
            // Mysql会重构成: select * from user LIMIT ?, ?
            // 而且每次除了执行重构的sql,还会执行: SELECT count(0) FROM user ,来统计总的记录数
            List<User> users = mapper.selectAll();
            System.out.println("查询出的大小: " + users.size());
            for (User user : users) {
                System.out.println(user);
            }

            // 第一种获取当前分页的详细信息的方法
			System.out.println("当前页码："+page.getPageNum());
			System.out.println("总记录数："+page.getTotal());
			System.out.println("每页的记录数："+page.getPageSize());
			System.out.println("总页码："+page.getPages());
        } finally {
            openSession.close();
        }
    }

    // 第二种获取分页详细信息的方法:
    @Test
    public void test02() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = openSession.getMapper(UserMapper.class);

            // 只需要调用PageHelper.startPage(2, 10);就可以分页了.
            Page<Object> page = PageHelper.startPage(6, 2); // 获取第6页，每页2条内容，默认会查询总数count
            List<User> users = mapper.selectAll();
            System.out.println("查询出的大小: ==========>" + users.size());
            for (User user : users) {
                System.out.println(user);
            }

            //用PageInfo对结果进行包装
            // 第2个参数: 传入要连续显示多少页,因为上面 PageHelper.startPage(6, 2),
            // 所以这里就是从第6也开始,每页显示2个items,连续显示5页.(pagehelper可以非常方便的做出强大的分页逻辑)
            PageInfo<User> pageInfo = new PageInfo<>(users, 5); // 分页导航,连续显示几页.有了这个分页插件之后,就很方便显示了

            //测试PageInfo全部属性
            //PageInfo包含了非常全面的分页属性
            assertEquals(true, pageInfo.isHasNextPage());
            System.out.println("当前页码："+pageInfo.getPageNum());
            System.out.println("每页记录数："+pageInfo.getPageSize());
            System.out.println("pageInfo.getStartRow() 从第几条记录开始："+pageInfo.getStartRow());
            System.out.println("pageInfo.getEndRow() 从第几条记录结束"+pageInfo.getEndRow());
            System.out.println("每页的记录数："+pageInfo.getPageSize());
            System.out.println("总记录数："+pageInfo.getTotal());
            System.out.println("总页码："+pageInfo.getPages());

            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");

            System.out.println("是否第一页："+pageInfo.isIsFirstPage());
            System.out.println("是否最后一页："+pageInfo.isIsLastPage());

            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");

            System.out.println("是否有下一页："+pageInfo.isHasNextPage());
            System.out.println("是否有前一页："+pageInfo.isHasPreviousPage());

            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");

            System.out.println("获取连续分页的第一页的页面："+pageInfo.getNavigateFirstPage());
            System.out.println("获取连续分页的最后一页的页面："+pageInfo.getNavigateLastPage());

            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");
            System.out.println("=======================>>>>>>");
            System.out.println("连续显示的页码：");
            int[] nums = pageInfo.getNavigatepageNums();
            for (int i = 0; i < nums.length; i++) {
                System.out.println("**********" + nums[i]);
            }
        } finally {
            openSession.close();
        }
    }

}
