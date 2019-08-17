package org.mybatis.core.springboot.service;

import org.mybatis.core.springboot.model.User;

public interface UserService {
    User selectUser(int id);
}

