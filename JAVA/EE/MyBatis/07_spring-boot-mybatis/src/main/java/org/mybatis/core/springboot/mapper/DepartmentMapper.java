package org.mybatis.core.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.mybatis.core.springboot.model.Department;


// 指定这是一个操作数据库的mapper
// @Mapper //如果不加这个@Mapper注解，则需要使用@MapperScan
// 本例中主应用类已经添加了@MapperScan,所以这里就不用@Mapper了
public interface DepartmentMapper {

    // 执行这个Java方法， MyBatis就会向数据库发出相应的SQL查询
    // 接口的方法名可以随意定义，关键在于@Select注解中的参数要与方法参数相对应。
    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    // 是不是使用自动生成的主键,true表示要使用自动生成主键. keyProperty告诉使用Department的哪个属性生成主键
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
