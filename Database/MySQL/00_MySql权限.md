```sql
-- 设置远程使用
-- root是用户名，%代表任意主机,'123456'指定的登录密码（这个和本地的root密码可以设置不同的，互不影响.
grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;

-- 重载系统权限
flush privileges;
exit;
```
