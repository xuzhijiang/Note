package com.jinxuliang.springbootjpademo.jpa;

import com.jinxuliang.springbootjpademo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JpaTeacherRepository extends JpaRepository<Teacher, Integer> {

    //遵循Jpa的惯例，定义查询方法
    List<Teacher> findAllByGender(String gender);

    //查询指定性别的大于指定岁数的用户
    List<Teacher> findAllByGenderAndAgeGreaterThan(String gender, int age);

    //按照主键进行查询
    Teacher getUserById(Integer id);

    //使用JPA查询语言自定义查询
    @Query("from teacher where name like concat('%', ?1, '%') ")
    public List<Teacher> findUsers(String userName);

    //直接使用原生的SQL命令
    @Query(value = "select * from Teacher where Teacher.gender=?1",nativeQuery = true)
    List<Teacher> findAllByGenderUseNativeSQL(String gender);

}