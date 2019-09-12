## 容器

image镜像文件生成的容器实例(container)，本身也是一个文件，称为容器文件。

也就是说，一旦容器生成，就会同时存在两个文件： image 文件和容器文件.

docker命令也这么分，分成对image的操作和对container的操作。

而且关闭容器并不会删除容器文件，只是容器停止运行而已。

```shell
# 列出本机正在运行的容器
$ docker container ls

# 列出本机所有容器，包括终止运行的容器
$ docker container ls --all

# 上面命令的输出结果包括容器的ID。很多地方都需要提供这个 ID，比如终止容器运行的命令
# kill命令相当于向容器里面的主进程发出 SIGKILL 信号
docker container kill [containerID]

# 新建容器，每运行一次，就会新建一个容器。同样的命令运行两次，就会生成两个一模一样的容器文件
docker container run

# 如果希望重复使用容器，就要使用start命令，它用来启动已经生成或者已经停止运行的容器文件
docker container start [containerID]

# docker container stop命令也是用来终止容器运行，
# 相当于向容器里面的主进程发出 SIGTERM 信号，然后过一段时间再发出 SIGKILL 信号。
# 这两个信号的差别是，应用程序收到 SIGTERM 信号以后，可以自行进行收尾清理工作，
# 但也可以不理会这个信号。如果收到 SIGKILL 信号，就会强行立即终止，那些正在进行中的操作会全部丢失。
docker container stop [containerID]

# 终止运行的"容器文件"，依然会占据硬盘空间，可以使用docker container rm命令删除
docker container rm [containerID]

#运行上面的命令之后，再使用docker container ls --all命令，就会发现被删除的容器文件已经消失了。

# 查看最近的运行容器
docker ps -l

# 查看所有正在运行的容器
docker ps -a

docker logs container_name

# 停止运行容器
docker stop container_id

# 用来查看 docker 容器的输出，即容器里面 Shell 的标准输出。
# 如果使用docker run命令运行容器的时候，没有使用-it参数，就要用这个命令查看输出。
docker container logs [containerID]

# 进入一个正在运行的 docker 容器。
# 如果docker run命令运行容器的时候，没有使用-it参数，就要用这个命令进入容器。
# 一旦进入了容器，就可以在容器的 Shell 执行命令了。
docker container exec -it [containerID] /bin/bash

# 用于从正在运行的 Docker 容器里面，将文件拷贝到本机。下面是拷贝到当前目录的写法。
docker container cp [containID]:[/path/to/file] .

# 我们也可以通过操作容器的名字来管理容器
docker start containerName.
docker restart containerName
docker stop containerName
```

# 进入容器

docker attach或 docker exec，推荐大家使用 docker exec 命令，原因会在下面说明。

## attach 命令

```shell
# 启动一个ubuntu容器
$ docker run -dit ubuntu containID

# 进入容器
$ docker attach 243c
# 注意： 如果从这个标准输入中 exit，会导致容器的停止。
```

## exec 命令

docker exec 后边可以跟多个参数，这里主要说明 -i -t 参数。

- 只用 -i 参数时，由于没有分配伪终端，界面没有我们熟悉的 Linux 命令提示符，但命令执行结果仍然可以返回。
- 当 -i -t 参数一起使用时，则可以看到我们熟悉的 Linux 命令提示符。

```shell
# 启动一个ubuntu容器
$ docker run -dit ubuntu containID  

# 进入容器
$ docker exec -i 69d1 bash
$ docker exec -it 69d1 bash
# 如果从这个 stdin 中 exit，不会导致容器的停止。
# 这就是为什么推荐大家使用 docker exec 的原因。 
```