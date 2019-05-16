### CentOS 7 更换阿里 yum源

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