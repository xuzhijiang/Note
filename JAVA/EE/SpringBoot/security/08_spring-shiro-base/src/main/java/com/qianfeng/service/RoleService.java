package com.qianfeng.service;

import com.qianfeng.dao.RoleDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RoleService {

    @Resource
    private RoleDAO roleDAO;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<String> queryAllRolenameByUsername(String username) {
        return roleDAO.queryAllRolenameByUsername(username);
    }
}
