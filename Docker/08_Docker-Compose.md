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

4.3 WordPress 示例
在docker-demo目录下，新建docker-compose.yml文件，写入下面的内容。


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
上面代码中，两个顶层标签表示有两个容器mysql和web。每个容器的具体设置，前面都已经讲解过了，还是挺容易理解的。

启动两个容器。


$ docker-compose up
浏览器访问 http://127.0.0.3:8080，应该就能看到 WordPress 的安装界面。

现在关闭两个容器。


$ docker-compose stop
关闭以后，这两个容器文件还是存在的，写在里面的数据不会丢失。下次启动的时候，还可以复用。下面的命令可以把这两个容器文件删除（容器必须已经停止运行）。


$ docker-compose rm