package com.qianfeng.service;

import java.util.Set;

public interface PermissionService {
    public Set<String> queryAllPermissionByUsername(String username);
}
