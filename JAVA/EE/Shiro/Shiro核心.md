- [shiro教程](https://www.vxzsk.com/766.html)

Apache Shiro 是一个强大易用的 Java 安全框架，提供了认证、授权、加密和会话管理等功能.
相比较 Spring Security，Shiro 小巧的多.对比 Spring Security，可能没有 Spring Security 做的功能强大，但是在实际工作时可能并不需要那么复杂的东西，所以使用小而简单的 Shiro 就足够了.

四大基石:

1. Authentication(认证)
2. Authorization(授权): 也叫访问控制，即在应用中控制谁能访问哪些资源（如访问页面/编辑数据/页面操作等）。在授权中需了解的几个关键对象：主体（Subject,即访问应用的用户）、角色（Role）、权限（Permission）、资源（Resource）
3. Session Management（会话管理）：特定于用户的会话管理,甚至在非web 或 EJB 应用程序
4. Cryptography（加密）：在对数据源使用加密算法加密的同时，保证易于使用。

Subject：当前用户
Realms：我们需要实现Realms的Authentication 和 Authorization。其中 Authentication 是用来验证用户身份，Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。

RBAC 是基于角色的访问控制(Role-Based Access Control)

```java
public interface SecurityManager extends Authenticator, Authorizer,SessionManager {}
//SecurityManager：管理所有Subject，SecurityManager是Shiro 架构的核心,
//SecurityManager继承了Authenticator,Authorizer,SessionManager接口，
//当一个Subject.login()的时候，默认情况下，SecurityManager继承的Authenticator的接口authenticate会被调用，里面会调用ModularRealmAuthenticator(默认使用 AtLeastOneSuccessfulStrategy策略)的authenticate，之后把相应的token传入，之后就调用到了Realm的认证方法来获取身份验证信息.
```

## Authenticator的工作原理

Authenticator会委托给相应的 AuthenticationStrategy 进行多
Realm 身份验证，默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy
进行多 Realm 身份验证；

Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，
如果没有返回 / 抛出异常表示身份验证失败了。此处可以配置多个 Realm，
将按照相应的顺序及策略进行访问。

SecurityManager 接口继承了 Authenticator，另外还有一个ModularRealmAuthenticator也实现了Authenticator，其委托给多个 Realm 进行验证，验证规则通过 AuthenticationStrategy 接口指定，默认提供的实现：

## securityManager.authenticator的authenticationStrategy(认证策略)

>Authenticator的职责是验证用户帐号，是Shiro API中身份验证核心的入口点，如果验证成功，将返回 AuthenticationInfo 验证信息；此信息中包含了身份及凭证；如果验证失败将抛出相应的 AuthenticationException 实现。

1. FirstSuccessfulStrategy:只要有一个Realm验证成功即可，只返回第一个身份验证成功的Realm的认证信息，其他的忽略
2. AtLeastOneSuccessfulStrategy：只要有一个Realm 验证成功即可，和 FirstSuccessfulStrategy不同，返回所有身份验证成功的Realm的认证信息
3. AllSuccessfulStrategy：所有Realm 验证成功才算成功，且返回所有 Realm 的认证信息，如果有一个失败就失败了

```java
// Shiro 支持三种方式的授权：
Subject subject = SecurityUtils.getSubject();
if(subject.hasRole(“admin”)) {
    //有权限
} else {
    //无权限
}

注解式：通过在执行的 Java 方法上放置相应的注解完成：
// 没有权限将抛出相应的异常；
@RequiresRoles("admin")
public void hello() {
    //有权限
};


//JSP/GSP 标签：在 JSP/GSP 页面通过相应的标签完成：

<shiro:hasRole name="admin">
<!— 有权限 —>
</shiro:hasRole>;
```

我们定义Realm一般继承 AuthorizingRealm（授权）即可；其继承了 AuthenticatingRealm（即身份验证），
 * 而且也间接继承了 CachingRealm（带有缓存实现）。

# 字符串通配符权限
# 规则：“资源标识符：操作：对象实例 ID” 即对哪个资源的哪个实例可以进行什么操作。
# 其默认支持通配符权限字符串，“:”表示资源/操作/实例的分割；
#“,”表示操作的分割；“*”表示任意资源/操作/实例。

# 1、单个资源单个权限: subject().checkPermissions("system:user:update");
# 用户拥有资源“system:user”的“update”权限。

2、单个资源多个权限

`role41=system:user:update,system:user:delete`

然后通过如下代码判断

`subject().checkPermissions("system:user:update", "system:user:delete");``

用户拥有资源“system:user”的“update”和“delete”权限。如上可以简写成：

ini 配置（表示角色4拥有 system:user 资源的 update 和 delete 权限）

role42="system:user:update,delete"

接着可以通过如下代码判断

subject().checkPermissions("system:user:update,delete");

3、单个资源全部权限

role51="system:user:create,update,delete,view"

`subject().checkPermissions("system:user:create,delete,update:view");``

用户拥有资源“system:user”的“create”、“update”、“delete”和“view”所有权限。如上可以简写成：

ini 配置文件（表示角色 5 拥有 system:user 的所有权限）

role52=system:user:*

也可以简写为（推荐上边的写法）：

role53=system:user

然后通过如下代码判断

subject().checkPermissions("system:user:*");
subject().checkPermissions("system:user");
通过“system:user:*”验证“system:user:create,delete,update:view”可以，但是反过来是不成立的。

4、所有资源全部权限

`role61=*:view`

`subject().checkPermissions("user:view");`
用户拥有所有资源的“view”权限。

假设判断的权限是“"system:user:view”，那么需要“role5=::view”这样写才行

5、实例级别的权限

单个实例单个权限

role71=user:view:1:对资源 user 的 1 实例拥有 view 权限。

然后通过如下代码判断

subject().checkPermissions("user:view:1");

单个实例多个权限:role72="user:update,delete:1"

对资源 user 的 1 实例拥有 update、delete 权限。

然后通过如下代码判断

subject().checkPermissions("user:delete,update:1");
subject().checkPermissions("user:update:1", "user:delete:1");

单个实例所有权：role73=user:*:1

对资源 user 的 1 实例拥有所有权限。

然后通过如下代码判断

subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");

所有实例单个权限
ini 配置

role74=user:auth:*

对资源 user 的 1 实例拥有所有权限。

然后通过如下代码判断

subject().checkPermissions("user:auth:1", "user:auth:2");

所有实例所有权限
ini 配置

role75=user:*:*

对资源 user 的 1 实例拥有所有权限。

然后通过如下代码判断

subject().checkPermissions("user:view:1", "user:auth:2");

6、Shiro 对权限字符串缺失部分的处理

如“user:view”等价于“user:view:*”；而“organization”等价于“organization:*”或者“organization:*:*”。可以这么理解，这种方式实现了前缀匹配。

另外如“user:*”可以匹配如“user:delete”、“user:delete”可以匹配如“user:delete:1”、“user:*:1”可以匹配如“user:view:1”、“user”可以匹配“user:view”或“user:view:1”等。即*可以匹配所有，不加*可以进行前缀匹配；但是如“*:view”不能匹配“system:user:view”，需要使用“*:*:view”，即后缀匹配必须指定前缀（多个冒号就需要多个*来匹配）。

8、性能问题

通配符匹配方式比字符串相等匹配来说是更复杂的，因此需要花费更长时间，但是一般系统的权限不会太多，且可以配合缓存来提升其性能，`如果这样性能还达不到要求我们可以实现位操作算法实现性能更好的权限匹配。`另外实例级别的权限验证如果数据量太大也不建议使用，可能造成查询权限及匹配变慢。可以考虑比如在sql查询时加上权限字符串之类的方式在查询时就完成了权限匹配。

### 授权流程

流程如下：

1. 首先调用 Subject.isPermitted*/hasRole*接口，其会委托给 SecurityManager，而 SecurityManager 接着会委托给 Authorizer；
2. Authorizer 是真正的授权者，如果我们调用如 isPermitted(“user:view”)，其首先会通过 PermissionResolver 把字符串转换成相应的 Permission 实例；
在进行授权之前，其会调用相应的 Realm 获取 Subject 相应的角色/权限用于匹配传入的角色/权限；
3. Authorizer 会判断 Realm 的角色/权限是否和传入的匹配，如果有多个 Realm，会委托给 ModularRealmAuthorizer 进行循环判断，如果匹配如 isPermitted*/hasRole* 会返回 true，否则返回 false 表示授权失败。
ModularRealmAuthorizer 进行多 Realm 匹配流程：

    * 首先检查相应的 Realm 是否实现了实现了 Authorizer；
    * 如果实现了 Authorizer，那么接着调用其相应的 isPermitted*/hasRole* 接口进行匹配；
    * 如果有一个 Realm 匹配那么将返回 true，否则返回 false。

如果 Realm 进行授权的话，应该继承 AuthorizingRealm，其流程是：

1. 如果调用 hasRole*，则直接获取 AuthorizationInfo.getRoles() 与传入的角色比较即可；首先如果调用如 isPermitted(“user:view”)，首先通过 PermissionResolver 将权限字符串转换成相应的 Permission 实例，默认使用 WildcardPermissionResolver，即转换为通配符的 WildcardPermission；
2. 通过 AuthorizationInfo.getObjectPermissions() 得到 Permission 实例集合；通过 AuthorizationInfo.getStringPermissions() 得到字符串集合并通过 PermissionResolver 解析为 Permission 实例；然后获取用户的角色，并通过 RolePermissionResolver 解析角色对应的权限集合（默认没有实现，可以自己提供）；
3. 接着调用 Permission.implies(Permission p) 逐个与传入的权限比较，如果有匹配的则返回 true，否则 false。

Authorizer、PermissionResolver及RolePermissionResolver

Authorizer 的职责是进行授权（访问控制），是 Shiro API 中授权核心的入口点，其提供了相应的角色/权限判断接口，具体请参考其 Javadoc。SecurityManager 继承了 Authorizer 接口，且提供了 ModularRealmAuthorizer 用于多 Realm 时的授权匹配。PermissionResolver 用于解析权限字符串到 Permission 实例，而 RolePermissionResolver 用于根据角色解析相应的权限集合。

我们可以通过如下 ini 配置更改 Authorizer 实现：

authorizer=org.apache.shiro.authz.ModularRealmAuthorizer
securityManager.authorizer=$authorizer&nbsp;
对于 ModularRealmAuthorizer，相应的 AuthorizingSecurityManager 会在初始化完成后自动将相应的 realm 设置进去，我们也可以通过调用其 setRealms() 方法进行设置。对于实现自己的 authorizer 可以参考 ModularRealmAuthorizer 实现即可，在此就不提供示例了。

设置 ModularRealmAuthorizer 的 permissionResolver，其会自动设置到相应的 Realm 上（其实现了 PermissionResolverAware 接口），如：

permissionResolver=org.apache.shiro.authz.permission.WildcardPermissionResolver
authorizer.permissionResolver=$permissionResolver&nbsp;
设置 ModularRealmAuthorizer 的 rolePermissionResolver，其会自动设置到相应的 Realm 上（其实现了 RolePermissionResolverAware 接口），如：

rolePermissionResolver=com.github.zhangkaitao.shiro.chapter3.permission.MyRolePermissionResolver
authorizer.rolePermissionResolver=$rolePermissionResolver&nbsp;

Subject 是 Shiro 的核心对象，基本所有身份验证、授权都是通过 Subject 完成。

1、身份信息获取

Object getPrincipal(); //Primary Principal
PrincipalCollection getPrincipals(); // PrincipalCollection&nbsp;
2、身份验证

void login(AuthenticationToken token) throws AuthenticationException;
boolean isAuthenticated();
boolean isRemembered();
通过 login 登录，如果登录失败将抛出相应的 AuthenticationException，如果登录成功调用 isAuthenticated 就会返回 true，即已经通过身份验证；如果 isRemembered 返回 true，表示是通过记住我功能登录的而不是调用 login 方法登录的。isAuthenticated/isRemembered 是互斥的，即如果其中一个返回 true，另一个返回 false。