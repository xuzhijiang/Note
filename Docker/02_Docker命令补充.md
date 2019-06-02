## 其他有用的命令

docker 的主要用法就是上面这些，此外还有几个命令，也非常有用。

前面的docker container run命令是新建容器，每运行一次，就会新建一个容器。同样的命令运行两次，就会生成两个一模一样的容器文件。如果希望重复使用容器，就要使用docker container start命令，它用来启动已经生成、已经停止运行的容器文件。
 
    $ docker container start [containerID]


前面的docker container kill命令终止容器运行，相当于向容器里面的主进程发出 SIGKILL 信号。而docker container stop命令也是用来终止容器运行，相当于向容器里面的主进程发出 SIGTERM 信号，然后过一段时间再发出 SIGKILL 信号。

    $ bash container stop [containerID]

这两个信号的差别是，应用程序收到 SIGTERM 信号以后，可以自行进行收尾清理工作，但也可以不理会这个信号。如果收到 SIGKILL 信号，就会强行立即终止，那些正在进行中的操作会全部丢失。



docker container logs命令用来查看 docker 容器的输出，即容器里面 Shell 的标准输出。如果docker run命令运行容器的时候，没有使用-it参数，就要用这个命令查看输出。

    $ docker container logs [containerID]


docker container exec命令用于进入一个正在运行的 docker 容器。如果docker run命令运行容器的时候，没有使用-it参数，就要用这个命令进入容器。一旦进入了容器，就可以在容器的 Shell 执行命令了。

    $ docker container exec -it [containerID] /bin/bash



docker container cp命令用于从正在运行的 Docker 容器里面，将文件拷贝到本机。下面是拷贝到当前目录的写法。

    $ docker container cp [containID]:[/path/to/file] .

```shell
# 通过 docker search 命令来查找官方仓库中的镜像
docker search nginx

# 根据是否是官方提供，可将镜像资源分为两类。
# 一种是类似 centos 这样的镜像，被称为基础镜像或根镜像。这些基础镜像由 Docker 公司创建、验证、支持、提供。这样的镜像往往使用单个单词作为名字。

#还有一种类型，比如 tianon/centos 镜像，它是由 Docker 的用户创建并维护的，往往带有用户名称前缀。可以通过前缀 username/ 来指定使用某个用户提供的镜像，比如 tianon 用户。

#另外，在查找的时候通过 --filter=stars=N 参数可以指定仅显示收藏数量为 N 以上的镜像。

$ docker search centos
NAME                                            DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
centos                                          The official build of CentOS.                   465       [OK]
tianon/centos                                   CentOS 5 and 6, created using rinse instea...   28
blalor/centos                                   Bare-bones base CentOS 6.5 image                6                    [OK]
saltstack/centos-6-minimal                                                                      6                    [OK]
tutum/centos-6.4                                DEPRECATED. Use tutum/centos:6.4 instead. ...   5                    [OK]
```

## 命令汇总

```shell
# 查看 Docker 版本
docker version
# 从 Docker 文件构建 Docker 映像
docker build -t image-name docker-file-location
# 运行 Docker 映像
docker run -d image-name
# 查看可用的 Docker 映像
docker images
# 查看最近的运行容器
docker ps -l
# 查看所有正在运行的容器
docker ps -a
# 停止运行容器
docker stop container_id
# 删除一个镜像
docker rmi image-name
# 删除所有镜像
docker rmi $(docker images -q)
# 强制删除所有镜像
docker rmi -r $(docker images -q)
# 删除所有虚悬镜像
docker rmi $(docker images -q -f dangling=true)
# 删除所有容器
docker rm $(docker ps -a -q)
# 进入 Docker 容器
docker exec -it container-id /bin/bash
# 查看所有数据卷
docker volume ls
# 删除指定数据卷
docker volume rm [volume_name]
# 删除所有未关联的数据卷
docker volume rm $(docker volume ls -qf dangling=true)
# 从主机复制文件到容器
sudo docker cp host_path containerID:container_path
# 从容器复制文件到主机
sudo docker cp containerID:container_path host_path
```

## 参考链接

- [http://www.ruanyifeng.com/blog/2018/02/docker-tutorial.html](http://www.ruanyifeng.com/blog/2018/02/docker-tutorial.html)