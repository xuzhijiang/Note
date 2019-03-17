package com.feiyangedu.springcloud.data.repository;

import com.feiyangedu.springcloud.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 在Spring-data-jpa中，只需要编写类似下面这样的接口就可实现数据访问。
 * 不再像我们以往编写了接口时候还需要自己编写接口实现类，直接减少了我们的文件清单。
 *
 * 下面对下面的UserRepository做一些解释，该接口继承自JpaRepository，
 * 通过查看JpaRepository接口的API文档，可以看到该接口本身已经实现了创建（save）、更新（save）、
 * 删除（delete）、查询（findAll、findOne）等基本操作的函数，因此对于这些基础操作的数据访问就不需要开发者再自己定义。
 *
 * 在我们实际开发中，JpaRepository接口定义的接口往往还不够或者性能不够优化，
 * 我们需要进一步实现更复杂一些的查询或操作。
 */
public interface MyUserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    // 可以看到我们这里没有任何类SQL语句就完成了两个条件查询方法。
    // 这就是Spring-data-jpa的一大特性：通过解析方法名创建查询。
    User findByNameAndGender(String name, Boolean gener);

    // 除了通过解析方法名来创建查询外，它也提供通过使用@Query 注解来创建查询，
    // 您只需要编写JPQL语句，并通过类似“:name”来映射@Param指定的参数
    // ，就像例子中的第三个findUser函数一样。
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
}
