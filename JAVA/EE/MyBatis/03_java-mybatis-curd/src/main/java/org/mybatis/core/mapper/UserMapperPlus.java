package org.mybatis.core.mapper;

import org.mybatis.core.model.User;

import java.util.List;

public interface UserMapperPlus {
    public User getUserById(Integer id);

    public User getUserAndDept01(Integer id);
    public User getUserAndDept02(Integer id);

    public User getUserByIdStep(Integer id);

    public List<User> getUsersByDeptId(Integer id);
}
