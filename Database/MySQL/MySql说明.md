其他关系数据库有所不同的是，MySQL本身实际上只是一个SQL接口，它的内部还包含了多种数据引擎,常用的包括：

* InnoDB：由Innobase Oy公司开发的一款支持事务的数据库引擎，2006年被Oracle收购；(支持事务)
* MyISAM：MySQL早期集成的默认数据库引擎，不支持事务。

>MySQL接口和数据库引擎的关系就好比浏览器和浏览器引擎(IE引擎或Webkit引擎）的关系。对用户而言，切换浏览器引擎不影响浏览器界面，切换MySQL引擎不影响自己写的应用程序使用MySQL的接口。

如果你不知道应该采用哪种引擎，记住总是选择InnoDB就好了.(支持事务)

### 管理MySQL

```sql
-- 设置远程使用
-- root是用户名，%代表任意主机,'123456'指定的登录密码（这个和本地的root密码可以设置不同的，互不影响.
grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;

-- 重载系统权限
flush privileges;
exit;
```
