# 容器化部署mysql

```shell script
docker search mysql

# 这里我们拉取官方的镜像
# 默认拉取mysql的latest版本
docker pull mysql

# 获取5.7.22的版本
docker pull mysql:5.7.22
# 运行容器(mysql5.7.22)：
# 下面映射了3个目录conf,logs,data,分别是配置文件目录,日志目录,数据目录.
# 注意,配置文件也要做成数据卷,因为其他容器也要共享使用.
# 这里有个问题,就是我们可能要修改初始的mysql容器配置文件
# 我们可以先启动一个容器,把mysql容器的所有配置拷贝到宿主机上,然后修改成我们自己的配置,然后把之前的mysql容器强制删除了,然后把我们的配置文件设置成数据卷,共享给新的mysql容器,
# 然后新容器就可以使用我们修改后的配置文件了.
docker run -p 3306:3306 --name mysql \
-v /usr/local/docker/mysql/conf:/etc/mysql \
-v /usr/local/docker/mysql/logs:/var/log/mysql \
-v /usr/local/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
-d mysql:5.7.22

# 或者使用下面简洁的命令(mysql8),mysql后面可以跟tag,类似于mysql:tag
# 没有tag默认就是latest
docker run -p 3306:3306 \
--name some-mysql \
-e MYSQL_ROOT_PASSWORD=my-secret-pw \
-d mysql
```

# 遇到的问题

启动mysql容器的时候,可能会遇到`Error response from daemon: Cannot start container b005715c40ea7d5821b15c44f5b7f902d4b39da7c83468f3e5d7c042e5fe3fbd: iptables failed: iptables --wait -t filter -A DOCKER ! -i docker0 -o docker0 -p tcp -d 172.17.0.43 --dport 80 -j ACCEPT: iptables: No chain/target/match by that name.
 (exit status 1)`的问题,解决方法:

```shell script
# 1. Clear all chains:
sudo iptables -t filter -F
sudo iptables -t filter -X

# 2. Then restart Docker Service:
systemctl restart docker
```

# docker mysql内存优化

docker mysql内存占用大概500M,经常自己挂掉,要优化,docker本身并不对内存有过多需求，这也就是为什么人家说比虚拟化要牛逼的原因之一，你要减少内存使用，那就要看你在docker里跑的是什么了，从应用入手.

这个不是 docker 的问题，一个 mysql 运行起来就需要差不多500M内存，如果内存不够就 OOM 退出喽

主要就是修改docker-mysql中的 /etc/mysql/conf.d/docker.cnf或者/etc/mysql/my.cnf

![](pics/docker-mysql内存优化-更改配置文件.png)

- [docker 安装 MySQL 8，并减少内存占用记录](https://www.bbsmax.com/A/xl56bo415r/)

# 修改mysql配置文件

```shell script
# 修改mysql初始可以接受的文件大小:
vim etc/mysql/mysql.conf.d
# 修改max_allowed_packet=128M,或者更大,这样就可以解决初始化数据失败的问题.
```
