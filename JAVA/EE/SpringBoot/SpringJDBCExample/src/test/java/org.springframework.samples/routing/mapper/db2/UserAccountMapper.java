package org.springframework.samples.routing.mapper.db2;

import com.legacy.springmvc.jdbc.routing.Routing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.samples.routing.entity.UserAccount;

// 接口或者类上使用@Routing注解，内部定义的方法将都会继承此注解

// 我们在UserAccountMapper接口上定义了@Routing注解，接口中定义的2个方法都会走ds2数据源访问db2，
// 而user_account位于db2中.

// 提示：通常我们一个Mapper接口操作的都是某个库中的表，因此建议直接在接口上添加@Routing注解，
// 而不是每个方法单独添加
@Routing("ds2")
public interface UserAccountMapper {

    @Select("select * from user_account where id = #{id}")
    @Routing("ds2")//通过@Routing注解，指定此方法走ds2数据源
    UserAccount selectById(@Param("id") int id);

    // 接口/方法上同时添加@Routing注解，方法上的@Routing注解优先于接口上的Routing注解
    @Insert("insert into user_account(account) values(#{account})")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)// 用于生成自增主键
    @Routing("ds1")//使用方法上@Routing注解指定的ds1数据源,注意：这是一个错误的示例，因为user_account表位于db2中
    int insert(UserAccount userAccount);
}
