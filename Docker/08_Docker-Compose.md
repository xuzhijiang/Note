# Docker Compose 简介

## 概念

[Compose](https://docs.docker.com/compose/) 是 Docker 公司推出的一个工具软件，可以管理多个 Docker 容器组成一个应用。你需要定义一个 YAML 格式的配置文件docker-compose.yml，写好多个容器之间的调用关系。然后，只要一个命令，就能同时启动/关闭这些容器。

```shell
# 启动所有服务
$ docker-compose up

# 关闭所有服务
$ docker-compose stop
```

## Docker Compose 的安装

Mac 和 Windows 在安装 docker 的时候，会一起安装 docker compose。[Linux 系统下的安装参考官方文档](https://docs.docker.com/compose/install/#install-compose)

>安装完成后，运行下面的命令。

```shell
$ docker-compose --version
```

## WordPress 示例

在docker-demo目录下，新建docker-compose.yml文件，写入下面的内容。

```shell
mysql:
    image: mysql:5.7
    environment:
     - MYSQL_ROOT_PASSWORD=123456
     - MYSQL_DATABASE=wordpress
web:
    image: wordpress
    links:
     - mysql
    environment:
     - WORDPRESS_DB_PASSWORD=123456
    ports:
     - "127.0.0.3:8080:80"
    working_dir: /var/www/html
    volumes:
     - wordpress:/var/www/html
```

>上面代码中，两个顶层标签表示有两个容器mysql和web,参数解释:

- environment: 向容器进程传入一个环境变量WORDPRESS_DB_PASSWORD，该变量会被用作数据库密码

>详细参数解释可以看: https://www.jianshu.com/p/658911a8cff3

```shell
# 启动两个容器
$ docker-compose up

# 浏览器访问 http://127.0.0.3:8080，应该就能看到 WordPress 的安装界面。

# 现在关闭两个容器
$ docker-compose stop
# 关闭以后，这两个容器文件还存在，写在里面的数据不会丢失。下次启动的时候，还可以复用。

# 下面的命令可以把这两个容器文件删除（容器必须已经停止运行）。
$ docker-compose rm
```

## docker-compose.yml示例

```shell
# This Compose file defines two services: web and redis.

# 表示该 Docker-Compose 文件使用的是 Version 2 file
version: '3'
services:
  web: # 指定服务名称
    build: . # 指定 Dockerfile 所在路径
    ports: # 指定端口映射
      - "5000:5000" # 第一个5000是外部服务器端口，第二个是容器内部的port
  redis:
    image: "redis:alpine"

# 在 docker-compose.yml 所在路径下执行命令就会自动构建镜像并使用镜像启动容器:
docker-compose up
docker-compose up -d  // 后台启动并运行容器
```

## Docker Compose 常用命令与配置

```shell
# ps：列出所有运行容器
docker-compose ps

# logs：查看服务日志输出
docker-compose logs

# 打印绑定的公共端口，下面命令可以输出 eureka 服务 8761 端口所绑定的公共端口
docker-compose port eureka 8761

# build：构建或者重新构建服务
docker-compose build

# start：启动指定服务已存在的容器
docker-compose start eureka

#stop：停止已运行的服务的容器
docker-compose stop eureka

# rm：删除指定服务的容器
docker-compose rm eureka

# up：构建、启动容器
docker-compose up

# kill：通过发送 SIGKILL 信号来停止指定服务的容器
docker-compose kill eureka

# scale：设置指定服务运气容器的个数，以 service=num 形式指定
docker-compose scale user=3 movie=3

# run：在一个服务上执行一个命令
docker-compose run web bash

```