package com.shiro.advanced;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

// 根据传入的 角色字符串 解析得到 应该使用哪个 Permission
public class CustomRolePermissionResolver implements RolePermissionResolver {

    /**
     * 根据传入的 角色字符串 解析得到 应该使用哪个 Permission
     * @param roleString 角色字符串
     * @return
     */
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if("role1".equals(roleString)) {
            Permission permission = new WildcardPermission("menu:*");
            return Arrays.asList(permission);
        } else {
            Permission permission = new BitPermission("");
            return Arrays.asList(permission);
        }
    }
}
