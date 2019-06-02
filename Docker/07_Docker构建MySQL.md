```shell
docker search mysql

# 这里我们拉取官方的镜像

docker pull mysql

# 运行容器：
docker run -p 3306:3306 --name mysql \
-v /usr/local/docker/mysql/conf:/etc/mysql \
-v /usr/local/docker/mysql/logs:/var/log/mysql \
-v /usr/local/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
-d mysql

# 命令参数：
# -p 3306:3306：将容器的3306端口映射到主机的3306端口
# -v /usr/local/docker/mysql/conf:/etc/mysql：将主机当前目录下的 conf 挂载到容器的 /etc/mysql
#-v /usr/local/docker/mysql/logs:/var/log/mysql：将主机当前目录下的 logs 目录挂载到容器的 /var/log/mysql
#-v /usr/local/docker/mysql/data:/var/lib/mysql：将主机当前目录下的 data 目录挂载到容器的 /var/lib/mysql
#-e MYSQL\_ROOT\_PASSWORD=123456：初始化root用户的密码

# 查看容器启动情况
docker ps

#使用客户端工具连接 MySQL

```