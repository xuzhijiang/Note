package com.springboot.cache.core.mapper;

import com.springboot.cache.core.domain.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {

    @Select("SELECT * FROM department WHERE id = #{id}")
    Department getDeptById(Integer id);
}
