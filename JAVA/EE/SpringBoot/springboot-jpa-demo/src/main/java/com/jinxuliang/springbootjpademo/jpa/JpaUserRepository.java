package com.jinxuliang.springbootjpademo.jpa;

import com.jinxuliang.springbootjpademo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// 定义数据存取接口:

// 使用Spring Data JPA存取数据库，需要通过数据接口实现，其方法有三种类型：

// 1. 直接使用系统提供的接口方法
// 2. 依据JPA约定编写自定义查询方法
// 3. 直接指定查询命令的查询方法

// JpaRepository定义的所有方法(比如findAll和findById方法),
// 均可以直接使用，而根本不需要你写任何一行代码去实现这些功能！这就是Spring
// Data JPA所提供给你的“魔术”

// 更神奇的是， Spring Data JPA定义了一种特殊的“语言”，
// 只要你遵循它的“语法”设定你的查询方法名，你也不需要手写一行SQL命令，
// Spring Data JPA也能自动识别这些方法名字，并自动地帮你实现它，从
// 而你就可以直接使用这些方法来查询数据。

// 如果你觉得前面所介绍的“魔术”不太可控，你也可以为查询方法直
// 接执行要执行的SQL命令，或者使用JPA所专门定义的一种查询语言编
// 写查询话句，满足你的“控制欲”。

// 在实际开发中，通常从JpaRepository中派生出自己的数据存取接口，
// 无需编写具体的接口实现类，就可以访问关系型数据库。
// 如下:
public interface JpaUserRepository extends JpaRepository<User, Integer> {

    //遵循Jpa的惯例，定义查询方法
    List<User> findAllByGender(String gender);

    //查询指定性别的大于指定岁数的用户
    List<User> findAllByGenderAndAgeGreaterThan(String gender,int age);

    //按照主键进行查询
    User getUserById(Integer id);


    //使用JPA查询语言自定义查询
    @Query("from user where name like concat('%', ?1, '%') ")
    public List<User> findUsers(String userName);

    //直接使用原生的SQL命令
    @Query(value = "select * from User where User.gender=?1",nativeQuery = true)
    List<User> findAllByGenderUseNativeSQL(String gender);

}