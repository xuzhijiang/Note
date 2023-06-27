# Docker Compose安装tomcat

    注意yml中不要有制表符,不能按table,只能有空格.

# 不指定数据卷

```shell script
cd /usr/local/docker/tomcat && vim docker-compose.yml
docker-compose up -d
```

```shell script
# 这个docker-compose.yml是怎么写出来的? 直接根据官方的docker run命令后的参数改造过来的.
# 启动两个 Tomcat 容器，映射端口为 8080 和 9091
version: '3' # 这个不是随便写的,是docker-compose配置语言的版本
services: # 多个容器(服务)集合
  tomcat1: # 名字随便起,但是要有意义.
    restart: always # 代表总是开机启动
    image: tomcat # 使用哪个image
    container_name: compose-tomcat1 # 容器名
    ports: # 指定端口映射
      - 8080:8080 # 第一个5000是宿主机端口，第二个是容器port
  tomcat2:
    restart: always
    image: tomcat
    container_name: compose-tomcat2
    ports:
      - 9091:8080
```

# 指定数据卷

```shell script
cd /usr/local/docker/tomcat && vim docker-compose.yml
docker-compose up -d
```

```shell script
# 这个docker-compose.yml是怎么写出来的? 直接根据官方的docker run命令后的参数改造过来的.
# 启动两个 Tomcat 容器，映射端口为 8080 和 9091
version: '3' # 这个不是随便写的,是docker-compose配置语言的版本
services: # 多个容器(服务)集合
  tomcat1: # 名字随便起,但是要有意义.
    restart: always # 代表总是开机启动
    image: tomcat # 使用哪个image
    container_name: compose-tomcat1 # 容器名
    ports: # 指定端口映射
      - 8080:8080 # 第一个5000是宿主机端口，第二个是容器port
    volumes:
      - /usr/local/docker/tomcat/webapps/test:/usr/local/tomcat/webapps/test # 左边宿主,右边容器
    environment:
      TZ: Asia/Shanghai
  tomcat2:
    restart: always
    image: tomcat
    container_name: compose-tomcat2
    ports:
      - 9091:8080
```