## 校对规则

    校对规则：指定 某一字符集下的字符 如何进行比较: 主要用于排序和分组

查看当前数据库的校对规则:`show variables like 'collation%';`
查看数据库中所有的校对规则: `show collation;`

```sql
-- 校对集: 校对集用以排序
    SHOW COLLATION [LIKE 'pattern']     查看所有校对集
    COLLATE 校对集编码     设置校对集编码
```

创建一个数据库db2,字符集用gbk，校对规则用gbk_bin
create database db1 character set gbk collate gbk_bin;

查看中文校对规则
show collation like '%gb%';

show variables like 'validate_password%';

set global validate_password_length = 6;

Enter the following if the database user already exists.:

GRANT SELECT ON database.* TO user@'localhost';

To enable more options, you would separate them with a comma. So to enable SELECT, INSERT, and DELETE your syntax would look like this:

GRANT SELECT, INSERT, DELETE ON database TO username@'localhost' IDENTIFIED BY 'password';