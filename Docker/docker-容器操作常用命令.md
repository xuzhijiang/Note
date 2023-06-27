# 容器

```shell script
# 进入一个正在运行的 docker 容器。
# 如果docker run命令运行容器的时候，没有使用-it参数，就要用这个命令进入容器。
# 一旦进入了容器，就可以在容器的 Shell 执行命令了。
docker container exec -it [containerID] /bin/bash

# 用于从正在运行的 Docker 容器里面，将文件拷贝到本机。下面是拷贝到当前目录的写法。
docker container cp [containID]:[/path/to/file] .

# 从宿主机复制文件到容器
docker cp host_path containerID:container_path
# 从容器复制文件到宿主机
docker cp containerID:container_path host_path
```

# 进入容器

docker attach或docker exec,我们一般使用docker exec命令，因为使用attach进入容器后,如果exit了,会导致容器的停止.

## exec 命令

- -i表示interactive,交互式的,-t代表termination,表示终端.所以当使用-i -t 参数一起使用时，就可以使用命令进行操作.
- -d 容器启动后，在后台运行
- -p 指定端口映射:左边是宿主机端口,右边是容器端口
- -P 大p指的是使用宿主机上的随机的端口.
- --name wordpress：容器的名字叫做wordpress,如果启动容器的时候没有指定容器的名字,就会有一个随机的名字
- --env MYSQL_ROOT_PASSWORD=123456：向容器进程传入一个环境变量MYSQL_ROOT_PASSWORD，该变量会被用作 MySQL 的根密码
- -e: 设置环境变量.

![](pics/深刻理解服务是没有界面的,进而理解docker的守护态-也就是不占用和用户交互的前台进程,而在后台服务.png)

```shell script
# 直接从image运行创建一个容器,这里是创建了一个新的容器,并且把容器启动起来
# 注意,run命令只要不加containerID,就会新创建一个容器.
# bash表示直接进入这个容器的终端
# --rm: 是一个可选参数,表示使用exit退出容器后，自动删除容器文件
# run -it: 以交互的方式启动一个容器,并进入容器
# 省略tag默认为latest
docker run [-it] [--rm] image-name[:tag] bash

# 使用随机端口启动tomcat,tomcat容器默认expose的是8080端口,我们启动后可以通过docker ps看到,
# 有一个随机的宿主机的port映射到了tomcat容器里面的8080.假设是37862端口,所以外界要通过37862访问tomcat容器.
# 也可以看到容器化部署本身就提供了一种安全性.
# 这里,大p指的是使用宿主机上的随机的端口来映射tomcat默认expose的8080端口.
docker run -P tomcat
```