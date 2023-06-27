package com.qianfeng.realm;

import com.qianfeng.pojo.User;
import com.qianfeng.service.PermissionService;
import com.qianfeng.service.RoleService;
import com.qianfeng.service.UserService;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Set;


@Setter
public class MyRealm extends AuthorizingRealm{

    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;

    /**
     * 作用：查询权限信息,并返回即可，不用任何比对
     * 何时触发：  /user/query = roles["admin"]  /user/insert= perms["user:insert"]   <shiro:hasRole  <shiro:hasPermission
     * 查询方式：通过用户名查询，该用户的 权限/角色 信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("在realm中 查询权限");
        // 获取当前用户的用户名
        String username = (String)principals.getPrimaryPrincipal();
        // 查询当前用户的所有 权限信息： RoleService: public List<String:RoleName> queryAllRolesByUsername(String username)
        //                          PermissionService: public List<String:PermissionStr> queryAllPermissionsByUsername(String username)
        //RoleService roleService = ContextLoader.getCurrentWebApplicationContext().getBean("roleServiceImpl", RoleService.class);
        //PermissionService permissionService = ContextLoader.getCurrentWebApplicationContext().getBean("permissionServiceImpl", PermissionService.class);
        // 查询当前用户的权限信息
        Set<String> roles = roleService.queryAllRolenameByUsername(username);
        Set<String> perms = permissionService.queryAllPermissionByUsername(username);
        // 将查询出的信息 封装
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
        simpleAuthorizationInfo.setStringPermissions(perms);
        return simpleAuthorizationInfo;
    }

    /**
     * 作用：查询身份信息，并返回即可，不用任何比对
     * 查询方式：通过用户名查询用户信息。
     * 何时触发：subject.login(token);
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("在Realm中查询身份");
        // 获取用户登录时发送来的用户名
        String username = (String)token.getPrincipal();
        // 查询用户信息： UserService:public User queryUserByUsername(String username);
        //UserService userService = ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl", UserService.class);
        //查询到用户信息
        User user = userService.queryUserByUsername(username);
        //判断用户信息是否为null
        if(user==null){//不存在用户名
            return null;//在后续流程中会抛出异常(shiro底层会抛出UnKnowAccountException)
        }
        // 将用户信息封装在  AuthenticationInfo 中,这个信息是用户真实的存在的信息
        // 然后shiro会拿着这个真实用户信息去和前端提交上来的信息去进行比对
        /*return new SimpleAuthenticationInfo(user.getUsername(),//数据库中用户名
                                     user.getPassword(), //数据库中的密码
                                     this.getName());*/// realm的标识
        return new SimpleAuthenticationInfo(user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                this.getName());

    }
}
