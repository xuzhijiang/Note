MySQL Client的可执行程序是mysql，MySQL Server的可执行程序是mysqld。

MySQL Client和MySQL Server的关系如下：

┌──────────────┐  SQL   ┌──────────────┐
│ MySQL Client │───────>│ MySQL Server │
└──────────────┘  TCP   └──────────────┘

>在MySQL Client中输入的SQL语句通过TCP连接发送到MySQL Server。

也可以只安装MySQL Client，然后连接到远程MySQL Server。假设远程MySQL Server的IP地址是10.0.1.99，那么就使用-h指定IP或域名：

`mysql -h 10.0.1.99 -u root -p`

其他关系数据库有所不同的是，MySQL本身实际上只是一个SQL接口，它的内部还包含了多种数据引擎,常用的包括：

* InnoDB：由Innobase Oy公司开发的一款支持事务的数据库引擎，2006年被Oracle收购；
* MyISAM：MySQL早期集成的默认数据库引擎，不支持事务。

>MySQL接口和数据库引擎的关系就好比浏览器和浏览器引擎(IE引擎或Webkit引擎）的关系。对用户而言，切换浏览器引擎不影响浏览器界面，切换MySQL引擎不影响自己写的应用程序使用MySQL的接口。

如果你不知道应该采用哪种引擎，记住总是选择InnoDB就好了.(支持事务)

### MySql乱码

当插入数据或建表后发现乱码，应该通过以下方式进行排查：

1. 查看MySql编码格式:`SHOW VARIABLES LIKE '%char%'`
2. show create table <table-name>;
3. show create database <database_name>;

问题描述 :当向 MySQL 数据库插入一条带有中文的数据时，会出现乱码，即插入不成功或显示时是乱码。
(Fu*k,一切都是Windows的GBK搞的鬼，用Navicat直接插入就可以.)

可以先使用语句 show variables like 'character%';来查看当前数据库的相关编码集:
可以看到 MySQL 有六处使用了字符集，分别为：client 、connection、database、results、server 、system。其中与服务器端相关：database、server、system；与客户端相关：connection、client、results 。

了解了上面的信息我们来分析下乱码的原因，问题出在了当前的 CMD 客户端窗口，因为当前的 CMD 客户端输入采用 GBK 编码，而数据库的编码格式为 UTF-8，编码不一致导致了乱码产生。

#### 解决方法:

而当前 CMD 客户端的编码格式无法修改(我的一切问题都是由于使用了该死的windows的cmd导致，cmd客户端默认编码是gbk)，所以只能修改 connection、 client、results的编码集来告知服务器端当前插入的数据采用 GBK编码，而服务器的数据库虽然是采用 UTF-8 编码，但却可以通知客户端将客户端的 GBK 数据转换为 UTF-8 进行存储。可以使用如下语句来快速设置与客户端相关的编码集：set names gbk;

设置完成后即可解决客户端插入数据或显示数据的乱码问题了，但我们马上会发现这种形式的设置只会在当前窗口有效，当窗口关闭后重新打开 CMD 客户端的时候又会出现乱码问题；那么，如何进行一个一劳永逸的设置呢？在 MySQL 的安装目录下有一个 my.ini 配置文件，通过修改这个配置文件可以一劳永逸的解决乱码问题。在这个配置文件中 [mysql] 与客户端配置相关，[mysqld] 与服务器配置相关。默认配置如下： 
[mysql] 
default-character-set=utf8 
[mysqld] 
character-set-server=utf8 
这时只需要将下的默认编码 default-character-set=utf8 改为 default-character-set=gbk ，重新启动 MySQL 服务即可。

根据“name+id”来去重，distinct同时作用在了name和id上:`select distinct name, id from A`

根据name来去重，如果表中name有相同的，那么查询出的结果只返回一条记录:`select distinct name from A`

### 管理MySQL

`SHOW DATABASES;`

其中，information_schema、mysql、performance_schema和sys是系统库，不要去改动它们。

```sql
-- 为root账户设置密码
mysqladmin -u root password 'root'

-- 设置远程使用
-- root是用户名，%代表任意主机,'123456'指定的登录密码（这个和本地的root密码可以设置不同的，互不影响.
grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;
-- 重载系统权限
flush privileges;
exit;

-- 验证使用,注意-proot，密码就是root,-ppassword, 密码就是password
mysql -u root -proot
```
