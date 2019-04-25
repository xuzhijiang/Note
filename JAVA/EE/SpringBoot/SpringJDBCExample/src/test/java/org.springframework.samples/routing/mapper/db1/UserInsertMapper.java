package org.springframework.samples.routing.mapper.db1;

import com.journaldev.spring.jdbc.routing.Routing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.samples.routing.entity.User;

// 接口或者类上使用@Routing注解，内部定义的方法将都会继承此注解
@Routing("ds1")
public interface UserInsertMapper {

    // 使用接口上@Routing注解指定的ds1数据源
    @Insert("insert into user(name) values(#{name}")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true) // 用于生成自增主键
    public int insert(User user);
}

// 如果项目的目录结构划分的比较好，"操作不同的数据库"的Mapper接口，位于不同的包下。如：

// org.springframework.samples.routing.mapper.db1包下都是操作db1的Mapper接口
// org.springframework.samples.routing.mapper.db2包下都是操作db2的Mapper接口

// 此时你可以使用包级别的@Routing注解，在包下面创建一个package-info.java，
// 从而无需在每个接口上都定义@Routing注解