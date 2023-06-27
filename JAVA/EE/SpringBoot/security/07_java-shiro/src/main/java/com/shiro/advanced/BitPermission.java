package com.shiro.advanced;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authz.Permission;

/**
 *  规则
 *    +资源字符串+权限位+实例ID
 *
 *  以+开头 中间通过+分割
 *
 *  权限：
 *     0 表示所有权限
 *     1 新增 0001
 *     2 修改 0010
 *     4 删除 0100
 *     8 查看 1000
 *
 *  如 +user+10 表示对资源user拥有修改/查看权限
 * */
public class BitPermission implements Permission {
    // 资源字符串
    private String resourceIdentify;
    // 权限位
    private int permissionBit;
    // 实例ID
    private String instanceId;

    public BitPermission(String permissionString) {
        // 以+为分隔符 分割字符串 形成数组
        String[] array = permissionString.split("\\+");

        // 资源字符串
        if(array.length > 1){ resourceIdentify = array[1]; }
        if(StringUtils.isEmpty(resourceIdentify)) { resourceIdentify = "*"; }

        // 权限位
        if(array.length > 2) { permissionBit = Integer.valueOf(array[2]); }

        // 实例ID
        if(array.length > 3) { instanceId = array[3]; }
        if(StringUtils.isEmpty(instanceId)) { instanceId = "*"; }

        System.out.println("xzj BitPermission-------->" + this.toString());
    }

    @Override
    public boolean implies(Permission p) {
        if(!(p instanceof BitPermission)) { return false; }
        BitPermission other = (BitPermission) p;
        System.out.println("xzj p: " + p);
        if(!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) { return false; }

        if(!(this.permissionBit ==0 || (this.permissionBit & other.permissionBit) != 0)) { return false; }

        if(!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) { return false; }
        return true;
    }

    @Override
    public String toString() {
        return "BitPermission{" + "resourceIdentify='" + resourceIdentify + '\''
                + ", permissionBit=" + permissionBit + ", instanceId='" + instanceId
                + '\'' + "}";
    }
}
