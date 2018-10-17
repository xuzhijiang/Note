## 安装Nginx

```shell
apt-get install nginx
```

ubantu安装完Nginx后，文件结构大致为：

1. 所有的配置文件都在 /etc/nginx下；
2. 启动程序文件在 /usr/sbin/nginx下；
3. 日志文件在 /var/log/nginx/下，分别是access.log和error.log；
4. 并且在  /etc/init.d下创建了启动脚本nginx。

```shell
sudo /etc/init.d/nginx start    # 启动
sudo /etc/init.d/nginx stop     # 停止
sudo /etc/init.d/nginx restart  # 重启
sudo service nginx start
sudo service nginx stop
sudo service nginx restart
service ngnix status #查看nginx服务的状态
```

### Unix常用命令

```shell
du -d 1 -h # show the size of the current directory
sudo netstat -tlpn | grep 800 # Check that the chosen port is already in use.
mkdir -p dirname # recursively create directory.
sudo lsof -i TCP:80 #see what application is listening on port 80
```

### Shell脚本

![shell script guide](http://tldp.org/LDP/Bash-Beginners-Guide/html/sect_07_01.html)

shell中针对一个script.sh脚本，使用. script.sh, bash script.sh, source script.sh效果都是一致的

### the configuration of Java env

```shell
sudo mv <jdk_directory> /usr/java/
cd /etc/profile.d
sudo touch Java.sh
sudo vim Java.sh
```

add the content below:

```shell
export JAVA_HOME=/usr/java/<jdk_directory>
export PATH=$JAVA_HOME/bin:$PATH
```

### create a symbolic link

create a symlink at /usr/bin/bar which references the original file /opt/foo

`ln -s /opt/foo /usr/bin/bar`

### tcpdump

`tcpdump tcp -i eth1 -t -s 0 -c 100 and dst port ! 22 and src net 192.168.1.0/24 -w ./target.cap`

1. tcp: ip icmp arp rarp 和 tcp、udp、icmp这些选项等都要放到第一个参数的位置，用来过滤数据报的类型
2. -i eth1 : 只抓经过接口eth1的包, any表示任何网口都抓
3. -t : 不显示时间戳
4. -s 0 : 抓取数据包时默认抓取长度为68字节。加上-s 0 后可以抓到完整的数据包
5. -c 100 : 只抓取100个数据包
6. dst port ! 22 : 不抓取目标端口是22的数据包
7. src net 192.168.1.0/24 : 数据包的源网络地址为192.168.1.0/24, 表示需要匹配源地址的网络编号有24位的数据包
8. -w ./target.cap : 保存成pcap文件，方便用ethereal(即wireshark)分析, 即存储路径

