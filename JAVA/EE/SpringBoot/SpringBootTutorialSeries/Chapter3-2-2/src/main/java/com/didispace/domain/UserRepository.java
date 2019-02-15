package com.didispace.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Spring-data-jpa依赖于Hibernate,如果您对Hibernate有一定了解，下面内容可以毫不费力的看懂并上手使用Spring-data-jpa
 *
 * 只需要通过编写一个继承自JpaRepository的接口就能完成数据访问
 *
 * 针对User实体创建对应的Repository接口实现对该实体的数据访问
 *
 * 在Spring-data-jpa中，只需要编写类似下面这样的接口就可实现数据访问。
 * 不再像我们以往编写了接口时候还需要自己编写接口实现类，直接减少了我们的文件清单
 *
 * 该接口继承自JpaRepository，通过查看JpaRepository接口的API文档，
 * 可以看到该接口本身已经实现了创建（save）、更新（save）、删除（delete）、
 * 查询（findAll、findOne）等基本操作的函数，因此对于这些基础操作的数据
 * 访问就不需要开发者再自己定义。
 *
 * 在我们实际开发中，JpaRepository接口定义的接口往往还不够或者性能不够优化，
 * 我们需要进一步实现更复杂一些的查询或操作。
 * 由于本文重点在spring boot中整合spring-data-jpa，在这里先抛砖引玉简单介
 * 绍一下spring-data-jpa中让我们兴奋的功能，后续再单独开篇讲一下spring-data-jpa中的常见使用。
 *
 * Spring-data-jpa的能力远不止本文提到的这些，由于本文主要以整合介绍为主，
 * 对于Spring-data-jpa的使用只是介绍了常见的使用方式。诸如@Modifying操作、
 * 分页排序、原生SQL支持以及与Spring MVC的结合使用等等内容就不在本文中详细展开
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 实现了按name查询User实体,可以看到我们这里没有任何类SQL语句
     * 就完成了两个条件查询方法。这就是Spring-data-jpa的一大特性：通过解析方法名创建查询。
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 按name和age查询User实体
     * @param name
     * @param age
     * @return
     */
    User findByNameAndAge(String name, Integer age);

    /**
     * 除了通过解析方法名来创建查询外，它也提供通过使用@Query 注解来创建查询，
     * 您只需要编写JPQL语句，并通过类似“:name”来映射@Param指定的参数，
     * 就像findUser函数一样。
     * @param name
     * @return
     */
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);


}
