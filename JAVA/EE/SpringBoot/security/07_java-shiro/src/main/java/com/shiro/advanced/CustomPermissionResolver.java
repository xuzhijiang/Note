package com.shiro.advanced;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

// 根据传入的 权限字符串 解析得到 应该使用哪个 Permission
public class CustomPermissionResolver implements PermissionResolver {

    /**
     * 根据传入的 权限字符串 解析得到 应该使用哪个 Permission
     * @param permStr 权限字符串
     * @return
     */
    @Override
    public Permission resolvePermission(String permStr) {
        System.out.println("CustomPermissionResolver------->" + permStr);
        return permStr.startsWith("+") ? new BitPermission(permStr) : new WildcardPermission(permStr);
    }
}
