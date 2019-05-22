package org.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * 方法1：useGeneratedKeys、keyProperty、keyColumn
 *
 * useGeneratedKeys、keyProperty、keyColumn属性，仅能在<insert>或者< update>元素中使用。作用分别如下所示：
 *
 * useGeneratedKeys :这会令 MyBatis 使用 JDBC 的ResultSet.getGeneratedKeys 方法来取出由数据库内部生
 * 成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。
 *
 * keyProperty:唯一标记一个属性，MyBatis 会通过 ResultSet.getGeneratedKeys 的返回值，默认：unset。
 * 如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。
 *
 * keyColumn :通过生成的键值设置表中的列名，这个设置仅在某些数据库（像 PostgreSQL）是必须的，
 * 当主键列不是表中的第一列的时候需要设置。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。
 *
 * 如果你的数据库支持自动生成主键的字段（如MySQL和SQL Server），那么你可以设置 useGeneratedKeys=”true”，
 * 然后再把 keyProperty设置到目标属性上就OK了。如果你的数据库还支持多行插入, 你也可以传入一个数组或集合，
 * 并返回自动生成的主键。下面分别进行演示：
 *
 *  获取插入的单条记录的自增id：
 * <insert id="insert" parameterType="User" useGeneratedKeys="true" keyProperty="id">
 *     INSERT INTO user (name, age) VALUES (#{name}, #{age})
 * </insert>
 *
 *
 *
 * 获取批量插入的记录自增id:
 *
 * 需要注意的是：获取批量插入的数据自增id之前存在bug，直接mybatis3.4.0之后才修复，读者需要保证mybatis版本>=3.4.0
 *
 * <insert id="batchInsert" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
 *         INSERT INTO user (id, name, age) VALUES
 *         <foreach collection="list" item="user" index="index" separator="," >
 *             (#{user.id}, #{user.name}, #{user.age})
 *         </foreach>
 *     </insert>
 */
public class TestGeneratedKey {

    private static SqlSessionFactory sqlSessionFactory;

    private SqlSession sqlSession;

    private UserMapper userMapper;

    @BeforeClass
    public static void testBefore() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Before
    public void before() {
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("xzj");
        user.setAge(26);
        System.out.println("插入前 id:" + user.getId());
        sqlSession.insert("org.mybatis.core.mapper.UserMapper.insertByAutoIncrKey", user);
        System.out.println("插入后：" + user.getId());
    }

    @Test
    public void testBatchInsert() {
        User user1 = new User();
        user1.setName("DDD");
        user1.setAge(26);

        User user2 = new User();
        user2.setName("EEE");
        user2.setAge(26);

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);

        System.out.println("插入前：" + userList);
        sqlSession.insert("org.mybatis.core.mapper.UserMapper.batchInsert", userList);
        System.out.println("插入后：" + userList);
    }

    /*
     * 方法2：通过<selectKey>
     *
     * 对于`不支持自动生成类型的数据库`或`可能不支持自动生成主键的JDBC驱动`来说，MyBatis 有另外一种方法来生成主键。
     * 通过<selectKey>。如下：
     *
     * <insert id="insertSelectKey" parameterType="User">
     *         <selectKey keyProperty="id" resultType="int" order="AFTER">
     *             SELECT last_insert_id();
     *         </selectKey>
     *         INSERT INTO user ( name, age) VALUES ( #{name}, #{age})
     * </insert>
     *
     * 这里的<selectKey>的作用，在INSERT语句执行完成之后(order="AFTER")，执行sql: SELECT last_insert_id();语句，获得自增id。这是mysql提供的获取上一次插入记录的自增id的语法，其他数据库中可能不同。
     */
    @Test
    public void insertSelectKey() {
        User user = new User();
        user.setName("bbb");
        user.setAge(26);
        System.out.println("插入前:" + user);
        sqlSession.insert("org.mybatis.core.mapper.UserMapper.insertSelectKey", user);
        System.out.println("插入后：" + user);// 可以看到这里同样获取到了数据库自增id。
    }
}
