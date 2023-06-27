package org.mybatis.core.springboot.controller;

import org.mybatis.core.springboot.mapper.DepartmentMapper;
import org.mybatis.core.springboot.mapper.EmployeeMapper;
import org.mybatis.core.springboot.model.Department;
import org.mybatis.core.springboot.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return departmentMapper.getDeptById(id);
    }

    // http://127.0.0.1:9090/dept?departmentName=aaa
    @GetMapping("/dept")
    public Department insertDept(Department department){
        departmentMapper.insertDept(department);
        return department;
    }

    // http://127.0.0.1:9090/emp?lastName=aa&gender=1&email=xx@qq.com&dId=30
    @GetMapping("/emp")
    public Employee insertEmp(Employee employee){
        int affected = employeeMapper.insertEmp(employee);
        System.out.println("affected: " + affected);
        return employee;
    }

    // http://127.0.0.1:9090/emp/1 (employee表id字段是自增的,所以这里使用1)
    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        System.out.println("id: " + id);
        return employeeMapper.getEmpById(id);
    }
}