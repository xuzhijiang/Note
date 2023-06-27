package com.springboot.cache.core.service;

import com.springboot.cache.core.domain.Department;
import com.springboot.cache.core.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    // 缓存存储到 到名字为dept的Cache中
    @Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id) {
        System.out.println("查询部门"+id);
        return departmentMapper.getDeptById(id);
    }
}
