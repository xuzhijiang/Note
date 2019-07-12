## LINUX

### linux系统下/etc/profile.d和/etc/profile的关系

以配置java环境变量为例(这里只是按照jdk的一种方式而已，还有很多种方式，例如可以直接通过系统的yum包管理器去安装，或者通过rpm软件包管理器自动安装,我们这里是手动从oracle官网上下载rpm软件包，然后手动配置jdk的path),在/etc/profile.d/创建java.sh，然后输入:

```shell
export JAVA_HOME=/usr/java/jdk-8u101-linux-x64
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

>因为在/etc/profile中会遍历/etc/profile.d下的sh，然后自动source.

### RedHat命令

```shell
# yum 主要功能是更方便的添加/删除/更新RPM 包，自动解决包的倚赖性问题，便于管理大量系统的更新问题。

# 查看linxu系统的版本
cat /etc/os-release

# 查看Linux内核版本命令
cat /proc/version

uname -a

lsb_release -a

cat /etc/issue

# 这种方法只适合Redhat系的Linux(可以看到Centos的版本是哪个)
cat /etc/redhat-release

# centos安装wget, -y: answer yes for all questions.
yum -y install wget

# 查找文件,注意有的linux版本后面的关键字如果是模糊匹配，需要加引号，有的不用
# 所以我们统一使用引号包裹关键字.
find ./ -name "config*"
find . -name "*.xml"

# 递归查找所有包含hello的xml文件
find . -name "*.xml" | xargs grep "hello"

# 查找当前目录中的"文件名"包含所有sh文件.
ls -l | grep 'sh'

# 建立链接
ln

# 查看硬盘的大小以及使用情况
df -T -h

# 挂载软硬光区
mount -t /dev/fdxhdax/mnt/目录名

# 解除挂载
umount /mnt/目录名

# 解除所有挂载(此命令慎用)
umount -a

# 列出所有信号名称：
kill -l

# 终止进程,只有第9种信号(SIGKILL-强制终止)
# 才可以无条件终止进程，其他信号进程都有权利忽略
kill -9 进程ID号

# 先用ps查找进程，然后用kill杀掉：
# ps -ef | grep vim

# 查看内存的使用情况
free -h

# 查看所有的环境变量值
env
printenv

# 对比windows下查看GRADLE_HOME: echo %GRADLE_HOME%

# 关机
shutdown -h now
halt

# 查看已安装软件包,并且过滤java(前提是java是通过rpm安装的)
rpm -qa | grep java

# 安装软件
yum install xxx.rmp

# 删除软件
yum remove xxx.rmp

# 升级软件
yum update/upgrade

# 显示软件包依赖关系
yum deplist

# 列出和java相关的所有包(这些包不是本机安装的，而是网络上的): 
yum search java | grep 'java-'

# 安装包括javac,jre在内的java相关包
yum install java-1.7.0-openjdk*

# 通过rpm安装						
rpm -ivh somesoft.rpm

# 卸载 						
rpm -e somefost.rpm

# 查询安装信息	
rpm -qi somefost.rpm

查询安装后位置：			
rpm -ql somefost.rpm

# 修改域名和ip的映射
vi /etc/hosts

# java.net.InetAddress获取的主机名称是从
/etc/sysconfig/network中读取,然后会连接这个主机名称对应的域名(/etc/hosts)

#如果需要永久修改hostname可通过如下命令
vi /etc/sysconfig/network
修改其中的HOSTNAME项，不过此种方法需要重启后生效.

# 查看文件，包括隐藏文件
ls -alh

# 删除文件 包括其子文件
# -r表示向下递归，不管有多少目录一律删除
# -f表示强制删除，不做任何提示。
rm -rf file

# 切换用户
su -username

# 安装gcc
yum install gcc
gcc --version
```

### Ubuntu常用命令

```shell
apt-get install nginx
```

### Unix常用命令

```shell
# 查看java的位置
whereis java

netstat -tlpn | grep 800 # Check that the chosen port is already in use.

# 查看端口的监听状态
netstat -tulnp

# 通过netstat命令观察tcp的socket连接信息
netstat -an | grep 220.181.57.216

mkdir -p dirname # recursively create directory.

# 通过vmstat命令查看当前操作系统每秒的上下文切换次数:命令"vmstat 1 10"的含义是：每个1秒统计一次，统计10次后结束。其中cs那一列表示的就是上下文切换次数,cs是context switch的简写

# 创建符号链接,create a symlink at /usr/bin/bar which references the original file /opt/foo
ln -s /opt/foo /usr/bin/bar
```

### shell脚本

[shell script guide](http://tldp.org/LDP/Bash-Beginners-Guide/html/sect_07_01.html)

shell中针对一个script.sh脚本，使用`. script.sh`, `bash script.sh`, `source script.sh`效果都是一致的

`while true; do echo hello world; sleep 1; done`

### tcpdump

`tcpdump tcp -i eth1 -t -s 0 -c 100 and dst port ! 22 and src net 192.168.1.0/24 -w ./target.cap`

1. tcp: 协议过滤，除了tcp协议常用的还有过滤ip udp等协议。当没有写的时候表示过滤全部.
2. -i eth1 : 只抓经过接口eth1的包, any表示任何网口都抓.
3. -t : 不显示时间戳.
4. -s 0 : 抓取数据包时默认抓取长度为68字节。加上-s 0 后可以抓到完整的数据包.
5. -w ./target.cap : 保存的文件名.

使用wireshark如何根据host，port过滤?

### df

```shell
xuzhijiang@T2:/$ df -T -h
文件系统        类型                            把文件系统挂载在哪个目录下
Filesystem     Type      Size  Used Avail Use% Mounted on
udev           devtmpfs   47G     0   47G   0% /dev
/dev/sda3      ext4       22T  2.4T   19T  12% /
tmpfs          tmpfs      47G     0   47G   0% /sys/fs/cgroup
/dev/sda1      ext4      454M  139M  288M  33% /boot
# 把/dev/sda3这块硬盘挂载到/下，也就是/下的内容都存放在/dev/sda3这块硬盘上.

# 查看某一个文件夹的大小
df -T -h /boot/
```

## Android

```
# 查看sdk版本号: 
cat /proc/msp/sys

# 查看图层
dumpsys SurfaceFlinger --fps=1

# 查看广播
dumpsys |grep BroadcastRecord
```