package org.mybatis.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.core.model.User;

import java.util.List;

public interface UserMapperDynamicSQL {
    //携带了哪个字段查询条件就带上这个字段的值
    public List<User> getUsersByConditionIf(User user);

    public List<User> getUsersByConditionTrim(User user);

    public List<User> getUsersByConditionChoose(User user);

    // 以前更新的时候是更新全部字段,现在我们要根据条件判断,如果字段不为null,再更新.
    public void updateUser(User user);

    //查询员工id'在给定集合中的
    public List<User> getUsersByConditionForeach(@Param("ids")List<Integer> ids);

    public void addUsers(@Param("users") List<User> users);

    // mybatis内置参数测试
    public List<User> getUsersTestInnerParameter(User user);
}
