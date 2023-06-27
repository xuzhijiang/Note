>登录mysql，备份，以及从备份恢复:

```shell script
# 数据库备份(从cmd命令行备份db_blog这个数据库):
mysqldump -uroot -h127.0.0.1 --databases db_blog -p > d:\test.sql

# 数据库恢复(进入mysql控制台恢复),注意如果test.sql脚本中没有选择数据库，需要先选择数据库，如果没有数据库，要先创建数据库:
source d:\test.sql

# 数据库恢复(从cmd命令行恢复)
mysql -u root -p < schema.sql
```

