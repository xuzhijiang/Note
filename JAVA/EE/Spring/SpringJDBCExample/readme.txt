让我们创建一个简单的表，我们将在我们的应用程序中使用CRUD操作示例:

```sql
CREATE TABLE `Employee` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```