package com.springframework.datajpa.core.repository;

import com.springframework.datajpa.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// 定义数据存取接口:

// JpaRepository定义的所有方法(比如findAll和findById方法),
// 均可以直接使用，而不需要你写任何一行代码去实现这些功能！这就是Spring
// Data JPA所提供给你的“魔术”,Spring Data JPA也能自动识别这些方法名字，从而可以直接使用这些方法来查询数据。

// 在实际开发中，通常从JpaRepository中派生出自己的数据存取接口，
// 无需编写具体的接口实现类，就可以访问关系型数据库。
/**
 * 在Spring-data-jpa中，只需要编写下面这样的接口就可实现数据访问(过动态代理实现数据存取功能)。
 * 不再像我们以往编写了接口时候还需要自己编写接口实现类，直接减少了我们的文件清单。
 *
 * UserRepository，该接口继承自JpaRepository，通过查看JpaRepository接口，
 * 可以看到该接口本身已经实现了创建（save）、更新（save）、删除（delete）、查询（findAll、findOne）等基本操作的函数，因此对于这些基础操作的数据访问就不需要开发者再自己定义
 *
 * 在我们实际开发中，JpaRepository接口定义的接口往往还不够或者性能不够优化，
 * 我们需要进一步实现更复杂一些的查询或操作。
 */
public interface MyUserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    // 通过解析方法名来操作数据库
    User findByNameAndGender(String name, Boolean gener);

    // 它也提供通过使用@Query注解来创建Sql语句，只需要编写JPQL语句，并通过类似“:name”来映射@Param指定的参数
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
}
