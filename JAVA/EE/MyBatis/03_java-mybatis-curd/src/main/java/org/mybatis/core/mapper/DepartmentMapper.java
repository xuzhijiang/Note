package org.mybatis.core.mapper;

import org.mybatis.core.model.Department;

public interface DepartmentMapper {
    public Department getDeptById(Integer id);
    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
