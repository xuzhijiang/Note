package com.neo.config;

import com.neo.entity.SysPermission;
import com.neo.entity.SysRole;
import com.neo.entity.UserInfo;
import com.neo.sevice.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;


public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 返回一个唯一的Realm名字
     */
    @Override
    public String getName() {
        return "MyShiroRealm";
    }

    /**
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，
     * 直接返回null即可。
     *
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
        System.out.println("AuthorizationInfo-------: " + userInfo.getUsername());
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRole());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;

        // 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，
        // stringPermissions是从数据库查询的当前用户对应的权限
        //
        //authorizationInfo.setRoles(roles);
        //authorizationInfo.setStringPermissions(stringPermissions);

        //就是说如果在shiro配置文件中添加了
        // filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
        // 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，
        // 如果在shiro配置文件中添加了
        // filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”);
        // 就说明访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问。
    }



    // Shiro的认证过程最终会交由Realm执行，这时会调用Realm的doGetAuthenticationInfo(token)方法。
    // 在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。
    //1、如果验证通过，就将返回一个封装了用户信息的AuthenticationInfo实例。
    //2、验证失败则抛出AuthenticationException异常信息。
    /**
     * 用来进行身份认证的，也就是验证用户输入的账号和密码是否正确
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println("username: " + username);
        System.out.println("--------token.getCredentials(): " + token.getCredentials());

        // 通过username从数据库中查找User对象
        // 实际项目中，这里可以根据实际情况做缓存，
        // 如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}