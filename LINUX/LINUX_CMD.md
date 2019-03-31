## 安装Nginx

```shell
apt-get install nginx
```

ubantu安装完Nginx后，文件结构大致为：

1. 所有的配置文件都在 /etc/nginx下；
2. 启动程序文件在 /usr/sbin/nginx下；
3. 日志文件在 /var/log/nginx/下，分别是access.log和error.log；
4. 并且在/etc/init.d下创建了启动脚本nginx。

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
通过vmstat命令查看当前操作系统每秒的上下文切换次数:命令"vmstat 1 10"的含义是：每个1秒统计一次，统计10次后结束。其中cs那一列表示的就是上下文切换次数,cs是context switch的简写

# 查看Linux内核版本命令
cat /proc/version
uname -a
lsb_release -a
cat /etc/issue
cat /etc/redhat-release(这种方法只适合Redhat系的Linux)
# centos安装wget
yum -y install wget

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
目前，它主要用作关键程序(如/bin/sh）的标准位置，以及需要在单用户模式下可用的基本命令二进制文件。

/sbin: /sbin，与/bin不同，用于mount /usr等分区之前所需的系统管理程序(普通用户通常不使用）,基本系统二进制文件(system bin),
uperuser (root) privileges required.

/usr/bin: 用于分发管理的普通用户程序。

/usr/sbin: /usr/sbin与/usr/bin具有相同的关系，和/sbin与/bin一样。

/usr/local/bin: 用于不由分发包管理器管理的普通用户程序，例如， 本地编译的包。 您不应将它们安装到/usr/bin中，因为将来的分发升级可能会在没有警告的情况下修改或删除它们。superuser (root) privileges required.

linux下paste code对齐命令:

按v，然后上下键，然后按=

### 配置应用的环境变量

RabbitMQ Server的命令会被安装到/usr/local/sbin，并不会自动加到用户的环境变量中去，所以我们需要在.bash_profile或.profile文件中增加下面内容：

>PATH=$PATH:/usr/local/sbin

这样，我们就可以通过rabbitmq-server命令来启动RabbitMQ的服务端了。

## shell脚本

`while true;do top -t -m 5;sleep 1;done`: 每隔1s打印出来前5个占用cpu的信息

查看某一个进程的cpu使用率:`top -p pid`

### RedHat命令

```shell
# 查找文件
find ./ -name config*

# 建立链接

ln
# 检查磁盘分区:
fdisk -l

# 检查硬盘使用情况
df -T -h

# 挂载软硬光区
mount -t /dev/fdxhdax/mnt/目录名

# 解除挂载
umount /mnt/目录名

# 解除所有挂载(此命令慎用)
umount -a

# 终止单一进程
kill 进程ID号

# 查看内存的使用情况
free

# 查看cpu的使用情况
top

# 查看环境变量值
env

# 关机
shutdown -h now
halt

# 查看已安装软件包
rpm -qa

:w file 将修改另外保存到file中，不退出vi

安装软件： 				yum install xxx.rmp
删除软件：				yum remove xxx.rmp
升级软件：				yum update/upgrade
显示软件包依赖关系：	    yum deplist

#列出和java相关的所有包的列表(不是本机安装的): 
yum search java | grep 'java-'

# 安装包括javac,jre在内的java相关包
yum install java-1.7.0-openjdk*

安装						
rpm -ivh somesoft.rpm

卸载 						
rpm -e somefost.rpm

查询						
rpm -q somefost.rpm

查询安装后位置：			
rpm -ql somefost.rpm

# 修改域名和ip的映射
vi /etc/hosts

# java.net.InetAddress获取的主机名称是从
/etc/sysconfig/network中读取,然后会连接这个主机名称对应的域名(/etc/hosts)

#如果需要永久修改hostname可通过如下命令
vi /etc/sysconfig/network
修改其中的HOSTNAME项，不过此种方法需要重启后生效。
```
