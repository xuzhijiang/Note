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

1. tcp: 协议过滤，除了tcp协议常用的还有过滤ip udp等协议。当没有写的时候表示过滤全部.
2. -i eth1 : 只抓经过接口eth1的包, any表示任何网口都抓.
3. -t : 不显示时间戳.
4. -s 0 : 抓取数据包时默认抓取长度为68字节。加上-s 0 后可以抓到完整的数据包.
5. -w ./target.cap : 保存的文件名.

### linux系统目录下bin的差异

如果系统支持某个命令，则在/system/bin或/system/sbin路径下会有该命令的二进制文件

sbin: The 's' in sbin means 'system'. Therefore, system binaries reside in sbin directories.

/bin: /bin和/sbin,用于在mounted较大的分区例如/usr等分区之前需要的在小的分区上的程序，
目前，它主要用作关键程序（如/bin/sh）的标准位置，以及需要在单用户模式下可用的基本命令二进制文件。

/sbin: /sbin，与/bin不同，用于mount /usr等分区之前所需的系统管理程序（普通用户通常不使用）,基本系统二进制文件(system bin),
uperuser (root) privileges required.

/usr/bin: 用于分发管理的普通用户程序。

/usr/sbin: /usr/sbin与/usr/bin具有相同的关系，和/sbin与/bin一样。

/usr/local/bin: 用于不由分发包管理器管理的普通用户程序，例如， 本地编译的包。 您不应将它们安装到/usr/bin中，因为将来的分发升级可能会在没有警告的情况下修改或删除它们。superuser (root) privileges required.

linux下paste code对齐命令:

按v，然后上下键，然后按=