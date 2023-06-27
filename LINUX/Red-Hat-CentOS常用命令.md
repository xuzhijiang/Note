# centos6常用服务类命令

![](pics/centos6-常用服务类(service)命令.png)

    centos6服务操作命令使用service
    
    查看服务的方法: 在/etc/init.d/目录下有对应的服务名
    
    查看服务是不是开机自启动的: chkconfig --list | grep xxx
    
    设置自启动用chkconfig --level 级别 服务名 on
    
    level中常用的是3和5,没有图形化界面是3,有图形化界面是5.

![](pics/运行级别分类.png)

# centos7常用服务类命令

![](centos7-常用服务类(service)命令.png)

    centos7服务操作使用命令systemctl
    
    查看服务状态: systemctl status service_name
    
    设置自启动用systemctl enable service_name

# yum命令

```shell
# yum 主要功能是更方便的添加/删除/更新软件，自动解决软件的倚赖性问题，便于管理大量系统的更新问题。

# centos安装wget, -y: answer yes for all questions.
yum -y install wget

# 安装软件
yum install xxx.rmp

# 卸载软件 (along with all its dependencies)
yum [-y] remove xxx.rmp

# 升级软件
yum update

# 显示软件包依赖关系
yum deplist

# 如果你不知道要安装的软件的package name,
# 可以用search搜索,将会搜索出所有的匹配关键字的包(不是本机安装的，而是网络上的): 
yum search java | grep 'java-'

# 展示安装包的信息
yum info samba-common.i686

# The following command will list all the packages available in the yum database.
yum list | less

# To view all the packages that are installed on your system, execute the following yum command.
yum list installed | less

# 安装包括javac,jre在内的java相关包
yum install java-1.7.0-openjdk*

# 安装gcc
yum install -y make cmake gcc gcc-c++
gcc --version
```

# rpm命令

```shell script
# 安装某种以rpm结尾的软件包,我希望有日志,进度条,有提示,使用-ivh					
rpm -ivh somesoft.rpm

# 卸载 						
rpm -e somefost.rpm

# 查询安装信息	
rpm -qi somefost.rpm

# 查询安装后位置	
rpm -ql somefost.rpm

# 查看某个软件是否被安装(前提是这个软件是通过rpm安装的)
rpm -qa | grep java

# 查看命令是属于哪个软件包的
rpm -qf /usr/bin/ab
```

# CentOS 7 更换阿里 yum源

```shell
# 备份你的原镜像文件，以免出错后可以恢复。
cp /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bac

# 下载新的CentOS-Base.repo 到/etc/yum.repos.d/
# CentOS 7系列
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

# 运行yum makecache生成缓存
yum clean all
yum makecache
```

# tomcat安装-centos

```shell
cd /opt/ && wget tomcat-download-url.tar.gz
tar -zxvf tomcat-download-url.tar.gz
ln -s tomcat-download-url tomcat

vim /etc/profile.d/tomcat.sh
# 增加:
export CATALINA_HOME=/opt/tomcat
export PATH=$CATALINA_HOME/bin:$PATH
# 导入到环境变量中
. /etc/profile.d/tomcat.sh
# 查看版本号信息
catalina.sh version
catalina.sh start
netstat -tunlp | grep java
# 可以访问了,注意关闭防火墙,或者打开8080端口
```

## 为Tomcat提供SysV脚本，方便使用、管理

```shell
vim /etc/rc.d/init.d/tomcat
# 添加shell项目下的tomcat-manage.sh内容.

chmod +x /etc/rc.d/init.d/tomcat

chkconfig --add tomcat
chkconfig tomcat --list

service tomcat version # 中间的tomcat就是上面的/etc/rc.d/init.d/tomcat脚本名字
```

# centos下开机自启服务

```shell script
# 开机自启mysql
chkconfig mysql on
# 关闭开机自启mysql
chkconfig mysql off
# 检查是否是开机自启
ntsysv
```