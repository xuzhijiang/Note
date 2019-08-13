package com.annotation.custom.jpa.service;

import com.annotation.custom.jpa.dao.BaseDao;
import com.annotation.custom.jpa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BaseDao<User> baseDao;

    public void add(User user) {
        baseDao.add(user);
    }

}
