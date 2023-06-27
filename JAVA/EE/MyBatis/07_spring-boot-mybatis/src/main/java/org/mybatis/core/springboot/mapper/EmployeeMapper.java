package org.mybatis.core.springboot.mapper;

import org.mybatis.core.springboot.model.Employee;

public interface EmployeeMapper {
    public int insertEmp(Employee employee);
    public Employee getEmpById(Integer id);
}
