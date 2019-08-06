package com.springframework.datajpa.core.repository;

import com.springframework.datajpa.core.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// 定义数据存取接口:

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
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    //遵循Jpa的惯例，定义查询方法
    List<Teacher> findAllByGender(String gender);

    //查询指定性别的大于指定岁数的用户
    List<Teacher> findAllByGenderAndAgeGreaterThan(String gender, int age);

    //按照主键进行查询
    Teacher getUserById(Integer id);

    //使用JPA查询语言自定义查询
    @Query("from teacher where name like concat('%', ?1, '%') ")
    public List<Teacher> findTeachers(String userName);

    //直接使用原生的SQL命令
    @Query(value = "select * from Teacher where Teacher.gender=?1",nativeQuery = true)
    List<Teacher> findAllByGenderUseNativeSQL(String gender);

}