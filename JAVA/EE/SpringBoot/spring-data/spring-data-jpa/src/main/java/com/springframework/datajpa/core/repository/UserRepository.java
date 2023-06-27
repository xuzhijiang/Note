package com.springframework.datajpa.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.datajpa.core.domain.User;

/**
 * JpaRepository其实也是继承了PagingAndSortingRepository，然后实现了自己的方法，
 * 我们这里编写自己的Repository，直接继承PagingAndSortingRepository
 *
 * JPA的传统配置在persistence.xml文件中，但是这里我们是在application.yml中
 */
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String name);

    // 通过解析方法名来操作数据库
    User findByNameAndGender(String name, Boolean gener);

    // 它也提供通过使用@Query注解来创建Sql语句，只需要编写JPQL语句，并通过类似“:name”来映射@Param指定的参数
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

}
