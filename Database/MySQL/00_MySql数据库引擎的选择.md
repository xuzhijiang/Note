# MySql接口和MySql引擎

和其他关系数据库有所不同的是，MySQL本身实际上只是一个SQL接口，它的内部还包含多种数据引擎,常用的包括：

* InnoDB：由Innobase Oy公司开发的一款支持事务的数据库引擎，2006年被Oracle收购；(支持事务)
* MyISAM：MySQL早期集成的默认数据库引擎，不支持事务。

其中 InnoDB 引擎有自有的日志模块 redolog 模块。**现在最常用的存储引擎是 InnoDB，它从 MySQL 5.5.5 版本开始就被当做默认存储引擎了。**

>MySQL接口和数据库引擎的关系就好比浏览器和浏览器引擎(IE引擎或Webkit引擎）的关系。对用户而言，切换浏览器引擎不影响浏览器界面，切换MySQL引擎不影响自己写的应用程序使用MySQL的接口。

如果你不知道应该采用哪种引擎，记住总是选择InnoDB就好了.(支持事务)

# only_full_group_by

MySQL 5.7.5后only_full_group_by成为sql_mode的默认选项之一，这可能导致之前的sql语句报错.

![](pics/only_full_group_by-01.png)
![](pics/only_full_group_by-02.png)
![](pics/only_full_group_by-03.png)

- [only_full_group_by研读](https://blog.csdn.net/allen_tsang/article/details/54892046)