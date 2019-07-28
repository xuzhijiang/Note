package com.springboot.beanscope.service;

import com.springboot.beanscope.bean.Employee;

public class EmployeeService {

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void destroy() throws Exception {
        System.out.println("EmployeeService destroy");
    }

    public void init() throws Exception {
        System.out.println("EmployeeService init");
        if(employee.getName() == null){
            employee.setName("xzj");
        }
    }

}
