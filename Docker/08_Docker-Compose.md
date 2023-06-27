# Docker Compose

学Docker,Compose是必会的.

[Compose](https://docs.docker.com/compose/) 定位是 「定义和运行多个 Docker 容器的应用」

![](pics/什么是DockerCompose.png)

你需要定义一个 YAML 格式的docker-compose.yml，写好多个容器之间的调用关系。然后，只要一个命令，就能同时启动/关闭这些容器

# Docker Compose 的安装

Mac 和 Windows 在安装 docker 的时候，会一起安装 docker compose。[Linux 系统下的安装参考官方文档](https://docs.docker.com/compose/install/#install-compose)

```shell script
docker-compose version
```

[安装与卸载](https://yeasy.gitbooks.io/docker_practice/compose/install.html)

# Docker Compose 常用命令

```shell script
# 注意docker-compose必须在有docker-compose.yml的目录下执行,如果当前目录没有这个yml文件
# 需要指定文件路径,例如docker-compose -f /usr/local/tomcat/docker-compose.yml up,但是一般不这么玩.

# 在 docker-compose.yml 所在路径下执行命令就会自动构建镜像并使用镜像启动容器:
# 前台运行
docker-compose up

# 启动并后台运行容器
docker-compose up -d

# ps：列出所有运行容器
docker-compose ps

# 守护态运行查看日志输出
docker-compose logs [container-name]

# 启动
docker-compose start

# 停止
docker-compose stop

# 停止并移除容器,这个命令可以在测试环境中使用,但是在生成环境中使用要注意,因为会把容器直接给删除了.
docker-compose down
```

# 参考

[Compose模板文件](https://yeasy.gitbooks.io/docker_practice/compose/compose_file.html)