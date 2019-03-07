```properties
jpa:
  hibernate:
    ddl-auto: create  
    # create表示每次都会自动删除原先存在的表，就是说如果表中存在数据，运
    # 行程序数据就不存在了。也可以是 update：会创建表，如果表中有数据，不会删除表
    # ，其中表名就是实体类的名字，表字段就是实体类的对应字段.
  show-sql: true
```

