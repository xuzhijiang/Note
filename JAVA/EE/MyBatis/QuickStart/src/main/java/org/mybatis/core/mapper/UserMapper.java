package org.mybatis.core.mapper;

import org.mybatis.core.model.User;
import java.util.List;

// UserMapper接口的全路径org.mybatis.core.mapper.UserMapper，
// 与UserMapper.xml中的namespace属性的值完全相同。

// 在UserMapper.xml中定义的各个元素<insert>、<delete>、<update>、<select>的id属性值
// 刚好是UserMapper接口中定义的几个方法的名字。

// 看到这里，读者可能已经知道mybatis想干啥了，我们不再需要在代码中，通过指定namespace.id方式，
// 告诉SqlSession去执行哪条sql了。直接操作UserMapper接口即可，mybatis内部会通过动态代理+反射技术，
// 获得当前执行的UserMapper接口的全路径，对应到UserMapper.xml中的namespace属性，
// 获得方法名对应到<insert>、<delete>、<update>、<select>的id属性。这样就可以知道要执行哪条sql了。

// 对于查询操作，mybatis会根据方法返回值类型判断要调用SqlSession的哪个方法，
// 如UserMapper接口中定义的selectById方法，其返回值是一个User对象，
// mybatis内部自动会调用SqlSession的selectOne方法，而对于selectAll这种返回值类型是List的情况，
// mybatis内部自动会调用SqlSession的selectList方法。

//另外，根据接口的方法声明中，是否包含参数，mybatis也可以自动判断是否需要调用
// SqlSession有parameter参数的方法重载形式。

//        可以通过以下方式获得UserMapper接口的动态代理对象
//
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//        UserMapper mapper = session.getMapper(UserMapper.class);
//        User user= mapper.selectById(1);
//        } finally {
//        session.close();
//        }
public interface UserMapper {

    public int insert(User user);
    public User selectById(int id);
    public List<User> selectAll();

    public int updateById(User user);
    public int deleteById(int id);

    public int deleteAll();
}