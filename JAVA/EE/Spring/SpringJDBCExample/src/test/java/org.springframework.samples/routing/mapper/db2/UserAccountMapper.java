package org.springframework.samples.routing.mapper.db2;

import com.journaldev.spring.jdbc.routing.Routing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.samples.routing.entity.UserAccount;

public interface UserAccountMapper {

    @Select("select * from user_account where id = #{id}")
    @Routing("ds2")
    UserAccount selectById(@Param("id") int id);

    @Insert("insert into user_account(account) values(#{account})")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)// 用于生成自增主键
    int insert(UserAccount userAccount);
}
