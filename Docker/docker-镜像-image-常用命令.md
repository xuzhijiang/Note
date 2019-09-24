## image文件

Docker 把应用程序及其依赖，打包在 image 文件里面。只有通过这个文件，才能生成 Docker container。

image 文件可以看作是container的模板。

Docker 根据 image 文件生成容器。同一个 image文件，可以生成多个同时运行的容器.

image 是二进制文件。实际开发中，一个 image 文件往往通过继承另一个 image 文件，加上一些个性化设置而生成。

举例来说，你可以在 Ubuntu 的 image 基础上，往里面加入 Apache 服务器，形成你的 image。

image 文件是通用的，一台机器的 image 文件拷贝到另一台机器，照样可以使用。

一般来说，为了节省时间，我们应该尽量使用别人制作好的 image 文件，而不是自己制作。即使要定制，也应该基于别人的 image 文件进行加工，而不是从零开始制作。

Docker 的官方仓库 Docker Hub 是最重要、最常用的 image 仓库。

此外，出售自己制作的 image 文件也是可以的。

```shell
# 列出本机的所有 image 文件。
$ docker image ls

# 删除 image 文件
$ docker image rm [imageName]

# 删除所有镜像
docker rmi $(docker images -q)

# 删除所有虚悬镜像(dangling image)
# 删除所有虚悬镜像的时候要把相关的容器都停止了,否则删不了.
docker image prune
# 如果基于虚悬镜像启动了容器,就删除失败了,要先删除了容器之后才可以删除.

# 删除所有虚悬镜像
docker rmi $(docker images -q -f dangling=true)
```