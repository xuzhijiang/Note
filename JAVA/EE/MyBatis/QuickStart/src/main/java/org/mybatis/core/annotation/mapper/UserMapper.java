package org.mybatis.core.annotation.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.core.model.User;

import java.util.List;

/**
 * mybatis的全局配置文件(mybatis-config.xml)和映射器配置(UserMapper.xml)都是基于xml文件格式配置的。
 * 事实上mybatis也支持将映射配置直接配置在Mapper接口中。此时修改：
 *
 * 修改mybatis-config.xml的mappers元素配置，用注解配合代替xml映射配置
 *
 * ```xml
 * <mappers>
 *         <!-- <mapper resource="mappers/UserMapper.xml"/> -->
 *         <!--通过class属性指定UserMapper接口的全路径-->
 *         <mapper class="org.mybatis.core.mapper.UserMapper"/>
 * </mappers>
 * ```
 *
 * 其中@Insert、@Select、@Update、@Delete注解，
 * 分别对应xml配置中的<insert>、<update>、<delete>、<select>元素。
 * 而注解里的sql直接就是把UserMapper.xml文件中对应的元素中的sql搬过来。
 *
 * 当然，mybatis中提供的注解远远不止这几个，我们接触的只是冰山一角。
 * mybatis提供的注解org.apache.ibatis.annotations包下面，在后面会对这些注解逐一进行讲述。
 */
public interface UserMapper {
    @Insert("INSERT INTO user(id,name,age) VALUES (#{id},#{name},#{age})")
    public int insert(User user);

    @Select(" select id,name,age from user where id= #{id}")
    public User selectById(int id);//返回值类型为User，所以调用SqlSession中的selectOne

    @Select(" select id,name,age from user")
    public List<User> selectAll();//返回值类型为List，所以调用SqlSession中的selectList

    @Update("UPDATE user SET name=#{name},age=#{age} WHERE id=#{id}")
    public int updateById(User user);

    @Delete("DELETE FROM user WHERE id=#{id}")
    public int deleteById(int id);
    @Delete("DELETE FROM user")
    public int deleteAll();
}
