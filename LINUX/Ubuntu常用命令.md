# 虚拟机的安装注意事项

磁盘分区的时候,一定要选择LVM技术,叫磁盘扩容技术.

![](pics/LVM硬盘扩容技术.png)

安装的时候一定不要配置自动更新软件,否则一旦你配置了自动更新,一旦你更新了软件,你就要重启机器,而且自动更新的频率是很高的,你的机器就要不断重启,一重启就要停止服务,这个是很可怕的.

- [虚拟机安装ubuntu server视频](https://www.bilibili.com/video/av29384041/?p=9)

# ubuntu版本

    版本	版本号	代号
    Lucid(10.04)	10.04	lucid
    Precise(12.04): precise	12.04	precise
    Trusty(14.04): trusty	14.04	trusty
    Utopic(14.10): utopic	14.10	utopic
    Ubuntu 16.04 TLS： xenial	16.04	xenial
    Ubuntu 18.04 TLS： bionic	18.04	bionic

# Ubuntu常用命令

```shell script
apt-get install nginx

# 卸载一个软件(以下这个命令会连同依赖一起卸载,这叫干净卸载)
apt-get autoremove nginx
```

# Ubuntu数据源

	/etc/apt/sources.list

# 修改软件数据源为阿里云

```shell
# 最好把原来的数据源备份
cp /etc/apt/sources.list /etc/apt/sources.list.bak
echo 'deb http://mirrors.aliyun.com/ubuntu/ xenial main restricted universe multiverse' > /etc/apt/sources.list
echo 'deb http://mirrors.aliyun.com/ubuntu/ xenial-security main restricted universe multiverse' >> /etc/apt/sources.list
echo 'deb http://mirrors.aliyun.com/ubuntu/ xenial-updates main restricted universe multiverse' >> /etc/apt/sources.list
echo 'deb http://mirrors.aliyun.com/ubuntu/ xenial-backports main restricted universe multiverse' >> /etc/apt/sources.list
apt-get update -y && apt-get clean
```

# Ubuntu安装jdk

```shell script
tar -zxvf jdk-8u152-linux-x64.tar.gz
mkdir -p /usr/local/java

# 移动安装包
mv jdk1.8.0_152/ /usr/local/java/

# 设置所有者
chown -R root:root /usr/local/java/

# 配置系统环境变量

# 1. 只为单个用户配置
vim ~/.bashrc
export JAVA_HOME=/usr/local/jdk
export CLASSPATH=./:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH:$HOME/bin

source ~/.bashrc

# 2. 为整个系统配置
vim /etc/profile
# 在中间加入
export JAVA_HOME=/usr/local/jdk
export CLASSPATH=./:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH:$HOME/bin

# 使用户环境变量生效
source /etc/profile

# 为其他用户更新用户环境变量
su lusifer
source /etc/profile

# windows上环境变量配置
JAVA_HOME: D:\Program Files\java\jdk-11.0.1
CLASSPATH: .;%JAVA_HOME%\lib;%JAVA_HOME%\jre\lib;
PATH: %JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
```

- [视频演示](https://www.bilibili.com/video/av27165645/)

## /etc/profile.d和/etc/profile的关系

在/etc/profile.d/创建java.sh，然后输入:

```shell
export JAVA_HOME=/usr/java/jdk-8u101-linux-x64
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

在/etc/profile中会遍历/etc/profile.d下的sh，然后自动source.

# Ubuntu安装tomcat以及优化

```shell script
# 1. 从官网下载tar.gz格式的通用安装包: https://tomcat.apache.org/
# 2. 解压: tar -zxvf **.tar.gz
cd /opt/apache-tomcat-7.0.96/bin
./startup.sh
./shutdown.sh
```

# ubuntu安装gcc和make

```shell script
# 安装c语言的编译器: gcc
apt-get install gcc

# 安装C++的编译器: g++
# configure: error: Invalid C++ compiler or C++ compiler flags
# 就需要安装C++的编译器:
apt-get install g++

# https://askubuntu.com/questions/192645/make-command-not-found
apt-get install make
```
