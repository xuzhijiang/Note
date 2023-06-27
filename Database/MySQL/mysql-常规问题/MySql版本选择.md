# MySql版本

>主流版本是5.x的,其中:

- 5.0-5.1: 是第4版的延申,早期产品的延续,也就是对早期产品的升级维护.
- 5.2-5.3: 用的特别少
- 5.4以后: 重要转变,也是企业里面用的最多的,msql整合了三方公司的新存储引擎,其中用的最多的是5.5,再就是5.7,本次课程基于5.5,一般推荐的也是5.5,因为比较稳定.而且功能也基本齐全.

# mysql不同版本中的语法不同

    only_full_group_by

    MySQL 5.7.5后only_full_group_by成为sql_mode的默认选项之一，这可能导致之前的sql语句报错.

![](../pics/only_full_group_by-01.png)
![](../pics/only_full_group_by-02.png)
![](../pics/only_full_group_by-03.png)
![](../pics/only_full_group_by-04.png)
![](../pics/only_full_group_by-05.png)

- [only_full_group_by研读](https://blog.csdn.net/allen_tsang/article/details/54892046)
