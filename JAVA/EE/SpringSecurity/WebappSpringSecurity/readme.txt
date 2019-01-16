Spring Security提供了在Web应用程序中执行身份验证和授权的方法。我们可以在任何基于servlet的Web应用程序中使用spring security。

## Spring Security

使用Spring Security的一些好处是：

1. 经过验证的技术，使用它比重新发明轮子更好。安全是我们需要特别小心的事情，否则我们的应用程序将容易受到攻击者的攻击。
2. 防止一些常见的攻击，如CSRF， session fixation attacks(会话固定攻击)
3. 易于集成到任何Web应用程序中。我们不需要修改Web应用程序配置，spring会自动将安全过滤器注入Web应用程序。
4. 通过不同方式提供对身份验证的支持 - 内存，DAO，JDBC，LDAP等等。
5. 提供忽略特定URL模式的选项，适用于提供静态HTML，图像文件。
6. 支持组和角色。

我们将创建一个Web应用程序并将其与Spring Security集成。

使用Eclipse中的“Dynamic Web Project”选项创建Web应用程序，以便我们的框架Web应用程序准备就绪。确保将其转换为maven项目，因为我们使用Maven进行构建和部署。

我们将研究三种Spring安全认证方法：

1. in-memory
2. DAO
3. JDBC

对于JDBC，我使用MySQL数据库并执行以下脚本来创建用户详细信息表:

```sql
CREATE TABLE `Employees` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Roles` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `role` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`username`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Employees` (`username`, `password`, `enabled`)
VALUES
    ('pankaj', 'pankaj123', 1);

INSERT INTO `Roles` (`username`, `role`)
VALUES
    ('pankaj', 'Admin'),
    ('pankaj', 'CEO');

commit;
```

我们还需要在servlet容器中将JDBC DataSource配置为JNDI.