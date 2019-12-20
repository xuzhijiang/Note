# Linux 远程控制管理

    windows上远程连接是通过win+R,然后输入mstsc调出远程连接界面的.

![](pics/远程控制管理01.png)

![](pics/远程控制管理02.png)

# 使用root用户远程登录

```shell script
# Linux 系统默认是关闭 Root 账户的,也就是默认root是没有密码的
# linux下登录一个账户必须要秘密,没有密码无法登录,这种是最安全的,因为外界不可能登录root账户
# 我们需要为 Root 用户设置一个初始密码才可以使用:
sudo passwd root

# 切换到root用户
su

# linux默认不允许远程登录root账号,所以要修改配置允许远程登录 Root
vim /etc/ssh/sshd_config
# 注释此行: # PermitRootLogin without-password
# 加入此行: PermitRootLogin yes   

# 重启服务
service ssh restart
```

>按照系统运维管理的规范,是不可以直接使用超级管理员的,必须使用代理账户或指定的权限账户.

    tips: guests: 来宾,客户,只能看,其他的删除,创建都没有权限.

