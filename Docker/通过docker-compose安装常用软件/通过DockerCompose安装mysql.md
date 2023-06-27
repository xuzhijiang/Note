# Docker Compose安装mysql

    注意yml中不要有制表符,不能按table,只能有空格.

# 指定数据卷

```shell script
cd /usr/local/docker/mysql && vim docker-compose.yml
docker-compose up -d
```

```shell script
# MySQL5
version: '3.1'
services:
  mysql:
    restart: always
    # 指定mysql的版本号,如果不指定就是使用最新的
    image: mysql:5.7.22
    container_name: mysql_myshop_container
    ports:
      - 3306:3306
    environment:
      TZ: Asia/Shanghai
      MYSQL_ROOT_PASSWORD: 123456
    command:
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_general_ci
      --explicit_defaults_for_timestamp=true
      --lower_case_table_names=1
      --max_allowed_packet=128M # 初始化参数
      --sql-mode="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO"
    volumes:
      - mysql-data:/var/lib/mysql #  给容器的/var/lib/mysql目录起了一个叫mysql-data的名字,启动后可通过docker volume ls查看到叫mysql-data的volume

# 数据卷可以单独管理,也可以统一管理,这是2种写法,这里mysql是统一管理
# tomcat的数据卷是单独管理的,这里的volumes是和services是对齐的.
volumes:
  mysql-data: # 这里的mysql-data在宿主机的路径是: /var/lib/docker/volumes/myshop_mysql-data/_data下.
```
