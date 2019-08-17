# linux用户管理

每个用户都属于某个用户组；一个组中可以有多个用户，一个用户也可以属于不同的组

当一个用户同时是多个组中的成员时，在/etc/passwd 文件中记录的是用户所属的主组，也就是登录时所属的默认组

## 在/etc/group 中的每条记录分四个字段：

用户组的所有信息都存放在/etc/group文件中.

/etc/group中的内容包括`用户组（Group）`、`用户组口令`、`GID(组id)`及该用户组所包含的用户（User），每个用户组一条记录；

1. 第一字段：用户组名称-group_name,组名不重复
2. 第二字段：用户组口令，一般不用,一般为空
3. 第三字段：GID(组标识号)
4. 第四字段：组内的用户列表.

![](https://www.cyberciti.biz/media/new/faq/2006/02/etc_group_file.jpg)

```shell
cat /etc/passwd 可以查看所有用户的列表

`w` 可以查看当前活跃的用户列表

cat /etc/group 查看用户组
```

# 添加某一个用户到"超级用户权限组

```shell
# Sudo不是一个组,有sudoer权限的用户/组被定义在一个配置文件中,你可以通过`sudo visudo`访问,

使用命令: sudo visudo(相当于vi /etc/sudoers)查看
# 注意: 要编辑这个文件,一定要使用visudo.

# 有好几种方式授予aa用户 sudo权限.

# 方式1：
# 在sudoers文件中找到有sudo权限的组，添加aa用户到这些组之一，假如你有以下一行在sudoers中:

# Members of the admin group may gain root privileges
%admin ALL=(ALL) ALL 
# 然后使用"usermod -a -G admin aa"添加aa到admin组

# 方式2：
# 创建一个新的名字叫bb组，然后把用户加到这个组中

# the 'sudo' group has all the sudo privileges
# bb组有所有sudo的权限
%bb    ALL=(ALL:ALL) ALL

# 方法3
# 使用下面的内容，为此用户在sudoers文件中设置一个特殊的rule(注意这里不带%开头，因为%代表的是一个组，不带%表示是一个用户)
aa用户 ALL=(ALL:ALL) ALL 

# Note that for all the rules I mentioned, I used the default ALL everywhere. The first one is the user(s) allowed, the second one is the host, the third one is the user as you are running the command and the last one is the commands allowed. You can tune your rules if ALL is too broad for your usecase.
# 请注意，对于我提到的所有规则，我在任何地方都使用默认的ALL。 第一个ALL是允许的用户，第二个是主机，第三个是运行命令时的用户，最后一个是允许的命令。 如果ALL对于您的用例而言过于宽泛，您可以调整规则。
```

# 创建一个新用户

```shell
# -m参数将会在/home/下创建zk用户.
# 注意创建一个用户的同时也会创建一个组,默认的组名和用户名是同名的
useradd zk -m

# 将bash设置为zk用户的默认shell：
usermod --shell /bin/bash zk

# 给zk设置密码
passwd zk

# 查看是否添加成功
cat /etc/passwd 

# 查看当前用户
who

# 查看新建的用户的权限
id zk

# 将zk添加到root组(或者改为有root权限的组也可以)
usermod -a -G root zk

# 切换到新建用户
su - zk

# 查看当前用户
who i am

# 删除一个用户
userdel zk

# 删除一个组
groupdel groupname
```
