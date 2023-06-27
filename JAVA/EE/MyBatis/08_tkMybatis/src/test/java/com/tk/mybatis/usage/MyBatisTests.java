package com.tk.mybatis.usage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tk.mybatis.usage.entity.TbUser;
import com.tk.mybatis.usage.mapper.TbUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
// 这里是为了能够加载MainApplication这个类使用的配置文件application.yml
@SpringBootTest(classes = MainApplication.class)
// 加上这俩注解后,测试完成后,数据库的数据不会有任何改动
@Transactional
@Rollback
public class MyBatisTests {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Test
    public void testInsert() {
        // 构造一条测试数据
        TbUser tbUser = new TbUser();
        tbUser.setUsername("xzjxxxxx");
        tbUser.setPassword("123456");
        tbUser.setPhone("15888888888");
        tbUser.setEmail("xxxx@qq.com");
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());

        // 插入数据
        tbUserMapper.insert(tbUser);
    }

    @Test
    public void testDelete() {
        // 构造条件，等同于 DELETE from tb_user WHERE username = 'Lusifer'
        Example example = new Example(TbUser.class);
        // 创建查询条件,上面传了一个Class
        // 所以这里通过反射找到username,然后和传入的value对比.如果找到相等的,就是找到了记录,执行下面的删除语句把匹配的记录删除了.
        example.createCriteria().andEqualTo("username", "Lusifer");

        // 删除数据
        tbUserMapper.deleteByExample(example);
    }

    /**
     * 测试修改数据
     */
    @Test
    public void testUpdate() {
        // 构造查询条件
        Example example = new Example(TbUser.class);

        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("username", "xuzhijiang01");

        // 构造一条测试数据
        TbUser tbUser = new TbUser();
        tbUser.setUsername("xzjxzjxzj");
        tbUser.setPassword("123456");
        tbUser.setPhone("15888888888");
        tbUser.setEmail("22996@qq.com");
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());

        // 修改数据
        tbUserMapper.updateByExample(tbUser, example);
    }

    /**
     * 测试查询集合
     */
    @Test
    public void testSelect() {
        // 构造查询条件
        Example example = new Example(TbUser.class);

        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("username", "xuzhijiang01");

        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andLike("email", "%xxxxx%");

        example.or(criteria2);

        List<TbUser> users = tbUserMapper.selectByExample(example);
        for (TbUser user : users) {
            System.out.println("*******" + user.getUsername());
        }
    }

    /**
     * 测试分页查询,这里借助了PageHelper进行分页,PageHelper的功能和tk.MyBatis的功能不重复
     */
    @Test
    public void testPage() {
        // PageHelper 使用非常简单，只需要设置页码和每页显示笔数即可
        PageHelper.startPage(0, 2);
        // 设置分页查询条件
        Example example = new Example(TbUser.class);

        // 获取查询结果
        List<TbUser> users = tbUserMapper.selectByExample(example);
        System.out.println("***********size: " + users.size());
        PageInfo<TbUser> pageInfo = new PageInfo<>(users);

        // pageInfo有很多方法
        System.out.println("当前页码: " + pageInfo.getPageNum());
        System.out.println("pageSize: " + pageInfo.getPageSize());
    }
}
