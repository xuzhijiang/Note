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
docker container kill [containerID]

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
```
