package com.spring.mybatis.core.tuling.mapper;

import com.spring.mybatis.core.tuling.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper {

    @Select("select * from employee")
    List<Employee> list();

    @Select("select * from employee where id=#{id}")
    Employee findOne(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into employee(last_name,email,gender,dept_id)values(#{lastName},#{email},#{gender},#{deptId})")
    int save(Employee employee);
}
