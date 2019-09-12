## 命令汇总

```shell
# --rm：容器终止运行后，自动删除容器文件
# --name wordpress：容器的名字叫做wordpress。
# --volume "$PWD/":/var/www/html：将当前目录（$PWD）映射到容器的/var/www/html（Apache 对外访问的默认目录）。因此，当前目录的任何修改，都会反映到容器里面，进而被外部访问到。

# -t 可以和终端交互，进入交互式终端.
# -d：容器启动后，在后台运行。
# --env MYSQL_ROOT_PASSWORD=123456：向容器进程传入一个环境变量MYSQL_ROOT_PASSWORD，该变量会被用作 MySQL 的根密码。

# 从 Docker 文件构建 Docker 映像
docker build -t image-name docker-file-location

# 运行 Docker 映像
docker run -d image-name

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