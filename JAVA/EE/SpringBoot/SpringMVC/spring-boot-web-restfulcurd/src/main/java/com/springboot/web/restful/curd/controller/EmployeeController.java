package com.springboot.web.restful.curd.controller;

import com.springboot.web.restful.curd.dao.DepartmentDao;
import com.springboot.web.restful.curd.dao.EmployeeDao;
import com.springboot.web.restful.curd.entities.Department;
import com.springboot.web.restful.curd.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    /*@ModelAttribute
    public void getEmployee( @RequestParam(value="id",required=false) Integer id,Map<String,Object> map){
        if(id!=null){
            map.put("employee", employeeDao.get(id));
        }
    }*/

    // 显示所有员工信息
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();
        // 放在请求域中 ( 默认存放到request域中)
        model.addAttribute("emps", employees);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxxx.html
        return "emp/list";
    }

    // 添加员工有2步操作:
    // 第一步通过get请求获取添加员工信息页面
    // 第二步是: 通过提交post请求新增员工.

    // 第一步: 显示添加员工信息页面
    @GetMapping("/emp")
    public String addPage(Model model) {
        //来到添加页面,查出所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    //第二步: 通过post请求新增添加
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        System.out.println("要保存的员工信息: " + employee);
        employeeDao.save(employee); // 保存员工
        return "redirect:/emps";  // 完成添加，重定向到 员工list 页面
    }

    // 修改功能和添加功能类似,有2步:
    // 第一步: 要先把要修改的员工信息先回显出
    // 第二步: 然后修改完了以后,点击修改,通过put请求来修改员工信息

    // 来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        System.out.println("要修改的员工: " + employee);
        model.addAttribute("emp", employee);

        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);

        // 回到修改页面(add是一个修改员工信息和添加员工二合一的页面);
        return "emp/add";
    }

    // 员工修改；需要提交员工id；
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        System.out.println("修改的员工数据："+employee);
        employeeDao.save(employee); // 用户名称不需要修改，会被置空
        return "redirect:/emps";
    }

    // 员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
