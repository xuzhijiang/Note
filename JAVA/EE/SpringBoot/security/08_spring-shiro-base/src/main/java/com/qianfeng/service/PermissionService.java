package com.qianfeng.service;

import com.qianfeng.dao.PermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PermissionService{

    @Autowired
    private PermissionDAO permissionDAO;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<String> queryAllPermissionByUsername(String username) {
        return permissionDAO.queryAllPermissionByUsername(username);
    }
}
