## image文件

```shell
# 获取镜像
# Docker 镜像仓库地址：地址的格式一般是 <域名/IP>[:端口号]。默认地址是 Docker Hub
# 仓库名是两段式名称，即 <用户名>/<软件名>。对于 Docker Hub，如果不给出用户名，则默认为 library，也就是官方镜像
docker pull [选项] [DockerRegistry地址[:端口号]/]仓库名[:标签]



# 删除所有虚悬镜像(dangling image)
# 删除所有虚悬镜像的时候要把相关的容器都停止了,否则删不了.
docker image prune
# 如果基于虚悬镜像启动了容器,就删除失败了,要先删除了容器之后才可以删除.

# 删除所有虚悬镜像
docker rmi $(docker images -q -f dangling=true)
```