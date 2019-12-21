package org.springboot.routing.datasource.core.advanced.mapper.db1;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springboot.routing.datasource.core.advanced.entity.User;

public interface UserSelectMapper {

    // 未添加注解，将走默认的数据源，因为在配置RoutingDataSource时，已经指定了默认的数据源是ds1，所以可以成功访问user表；
    // 注意：如果默认的数据源是ds2，此方法将会报错，因为user表位于db1中，而不是db2中
    @Select("select * from user where id = #{id}")
    public User selectById(@Param("id") int id);

}
