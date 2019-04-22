package org.springframework.samples.routing.mapper.db1;

import com.journaldev.spring.jdbc.routing.Routing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.samples.routing.entity.User;

@Routing("ds1")
public interface UserInsertMapper {

    @Insert("insert into user(name) values(#{name}")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true) // 用于生成自增主键
    public int insert(User user);
}
