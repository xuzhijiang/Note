```shell
# scp是secure copy的简写,scp传输是加密的
# scp [参数] [源原路径] [目标路径]

# 从本地服务器复制到远程服务器
scp local_file remote_username@remote_ip:remote_folder
scp local_file remote_username@remote_ip:remote_file
scp local_file remote_ip:remote_folder
scp local_file remote_ip:remote_file

# 指定了用户名，命令执行后需要输入用户密码；如果不指定用户名，命令执行后需要输入用户名和密码；

# 复制目录:
# 第1个指定了用户名，命令执行后需要输入用户密码； 
# 第2个没有指定用户名，命令执行后需要输入用户名和密码；
scp -r local_folder remote_username@remote_ip:remote_folder
scp -r local_folder remote_ip:remote_folder

# 从远程复制到本地的scp命令与上面的命令一样，只要将从本地复制到远程的命令后面2个参数互换顺序就行了。

# 从远处复制文件到本地目录
# 说明： 从10.6.159.147机器上的/opt/soft/的目录中下载demo.tar 文件到本地/opt/soft/目录中
scp root@10.6.159.147:/opt/soft/demo.tar /opt/soft/

# 从远处复制到本地
# 说明： 从10.6.159.147机器上的/opt/soft/中下载test目录到本地的/opt/soft/目录来。
scp -r root@10.6.159.147:/opt/soft/test /opt/soft/

# 上传本地文件到远程机器指定目录
# 说明： 复制本地opt/soft/目录下的文件demo.tar 到远程机器10.6.159.147的opt/soft/scptest目录
scp /opt/soft/demo.tar root@10.6.159.147:/opt/soft/scptest

# 上传本地目录到远程机器指定目录
# 说明： 上传本地目录 /opt/soft/test到远程机器10.6.159.147上/opt/soft/scptest的目录中
scp -r /opt/soft/test root@10.6.159.147:/opt/soft/scptest
```