# 从本地服务器复制到远程服务器

```shell script
# scp是secure copy的简写,scp传输是加密的
# scp [参数] [源原路径] [目标路径]

# 1. 把文件从本地服务器复制到远程服务器
# 指定了用户名，命令执行后需要输入用户密码；
scp local_file remote_username@remote_ip:remote_folder
scp local_file remote_username@remote_ip:remote_file
# 如果不指定用户名，命令执行后需要输入用户名和密码；
scp local_file remote_ip:remote_folder
scp local_file remote_ip:remote_file
# 示例:复制本地opt/soft/目录下的文件demo.tar 到远程机器10.6.159.147的opt/soft/scptest目录
scp /opt/soft/demo.tar root@10.6.159.147:/opt/soft/scptest

# 2. 把目录从本地服务器复制到远程服务器
# 指定了用户名，命令执行后需要输入用户密码； 
scp -r local_folder remote_username@remote_ip:remote_folder
# 没有指定用户名，命令执行后需要输入用户名和密码；
scp -r local_folder remote_ip:remote_folder
# 示例:拷贝本地目录 /opt/soft/test到远程机器10.6.159.147上/opt/soft/scptest的目录中
scp -r /opt/soft/test root@10.6.159.147:/opt/soft/scptest
```

# 从远程复制到本地

```shell script
# 从远程复制 文件 到本地目录
# 说明： 从10.6.159.147机器上的/opt/soft/的目录中下载demo.tar 文件到本地/opt/soft/目录中
scp root@10.6.159.147:/opt/soft/demo.tar /opt/soft/

# 从远处复制 目录 到本地
# 说明： 从10.6.159.147机器上的/opt/soft/中下载test目录到本地的/opt/soft/目录来。
scp -r root@10.6.159.147:/opt/soft/test /opt/soft/
```