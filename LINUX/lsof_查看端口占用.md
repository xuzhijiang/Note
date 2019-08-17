```shell
#  查看当前系统的端口使用：
netstat -an

# 查找某个文件相关的进程
lsof /bin/bash

# 列出某个用户打开的文件信息
lsof -u username

# 列出某个程序进程所打开的文件信息
# -c 选项将会列出所有以mysql这个进程开头的程序的文件
lsof -c mysql

# 通过某个进程号显示该进程打开的文件
lsof -p pid

# 列出所有的网络连接
lsof -i

# 列出所有tcp 网络连接信息
lsof -i tcp

# 列出谁在使用某个端口
lsof -i:3306

# 列出某个用户的所有活跃的网络端口
lsof -a -u test -i

lsof -i TCP:80 #see what application is listening on port 80
```

## windows上解决SpringBoot内嵌服务器端口占用的问题

1. netstat  -ano|findstr  8080(查看占用8080端口的进程）
2. taskkill  /pid  6856  /f (运行windows自带taskkill命令，结束掉进程)