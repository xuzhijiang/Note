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

![](../pics/docker-mysql内存优化-更改配置文件.png)

- [docker 安装 MySQL 8，并减少内存占用记录](https://www.bbsmax.com/A/xl56bo415r/)

# 修改mysql配置文件

```shell script
# 修改mysql初始可以接受的文件大小:
vim etc/mysql/mysql.conf.d
# 修改max_allowed_packet=128M,或者更大,这样就可以解决初始化数据失败的问题.
```
