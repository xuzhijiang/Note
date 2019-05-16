## linux用户管理

>将用户分组是Linux系统中对用户进行管理及控制访问权限的一种手段。

* 每个用户都属于某个用户组；一个组中可以有多个用户，一个用户也可以属于不同的组
* 当一个用户同时是多个组中的成员时，在/etc/passwd 文件中记录的是用户所属的主组，也就是登录时所属的默认组，而其他组称为附加组。
* 用户组的所有信息都存放在/etc/group文件中，此文件的格式是由冒号(:)隔开的若干个字段
* /etc/group中的内容包括`用户组（Group）`、`用户组口令`、`GID(组id)`及该用户组所包含的用户（User），每个用户组一条记录；

>在/etc/group 中的每条记录分四个字段：

1. 第一字段：用户组名称,组名不应重复
2. 第二字段：用户组密码,一般Linux系统的用户组都没有口令，即这个字段一般为空，或者是*
3. 第三字段：GID(组标识号与用户标识号类似，也是一个整数，被系统内部用来标识组)
4. 第四字段：组内用户列表(是属于这个组的所有用户的列表)，每个用户之间用逗号分割；这个用户组可能是用户的主组，也可能是附加组.本字段可以为空；如果字段为空表示用户组为GID的用户名

```shell
cat /etc/passwd 可以查看所有用户的列表

w 可以查看当前活跃的用户列表

cat /etc/group 查看用户组

# 添加某一个用户到"超级用户权限组"
# Sudo is not directly a group. The groups/users having sudoer rights are defined in a configuration file that you can access using `sudo visudo`.Check out this file to find out how it is configured on your system.

sudo visudo(相当于vi /etc/sudoers)

# In your case, you have different ways to give sudo rights to chauncey.
# 你有很多不同的方式给用户chauncey sudo权限.

# 方式1：
# find the group(s) having sudo rights in the sudoers file and add chauncey to one of these groups. For example, say you have this line in sudoers:
# 在sudoers文件中找到有sudo权限的组，添加chauncey用户到这些组之一，假如你有以下一行在sudoers中:

# Members of the admin group may gain root privileges
%admin ALL=(ALL) ALL 
# then, add chauncey to admin with sudo usermod -a -G admin chauncey.
# 那么就使用"usermod -a -G admin chauncey"添加chauncey到admin组

# 方式2：
# create a new sudo group (sudo groupadd sudo) and add this lines (sudo visudo). Then once again add chauncey to the group
# 创建一个新的sudo组，然后把用户加到这个组中

# the 'sudo' group has all the sudo privileges
# sudo组有所有sudo的权限
%sudo    ALL=(ALL:ALL) ALL 

# 方法3
# set a special rule for this user in the sudoers file using the following (note that there is no %, which is used to denote a group):
# 使用下面的内容，为此用户在sudoers文件中设置一个特殊的rule(注意不带%，因为%代表的是一个组，不带%表示是一个用户)
chauncey    ALL=(ALL:ALL) ALL 

# Note that for all the rules I mentioned, I used the default ALL everywhere. The first one is the user(s) allowed, the second one is the host, the third one is the user as you are running the command and the last one is the commands allowed. You can tune your rules if ALL is too broad for your usecase.
# 请注意，对于我提到的所有规则，我在任何地方都使用默认的ALL。 第一个ALL是允许的用户，第二个是主机，第三个是运行命令时的用户，最后一个是允许的命令。 如果ALL对于您的用例而言过于宽泛，您可以调整规则。
```

### 创建一个新用户

```shell
# 创建运行ZooKeeper服务的用户
# Passing the -m flag to the useradd command will create a home directory for this user. The home directory for zk will be /home/zk by default.
useradd zk -m

# Set bash as the default shell for the zk user:
# 将bash设置为zk用户的默认shell：
usermod --shell /bin/bash zk

# Set a password for this user:
passwd zk

# 查看是否添加成功
cat /etc/passwd 

# 查看当前用户
who

# 查看新建的用户的权限
id zk

# 赋予新建账号root权限（添加到root组里）
usermod -g root zk

#Next, you will add the zk user to the sudo group so it can run commands in a privileged mode:
# 这步有问题，所以用上一步就可以添加到root组中
usermod -aG sudo zk
# 这里有一篇介绍sudo很好的文章: https://stackoverflow.com/questions/40892646/when-iroot-excute-usermod-g-sudo-chauncey-it-say-that-sudo-group-doesnt-e

# 切换到新建用户
su - zk

# 查看当前用户
who i am

# 切换到新建用户
su - newuser

# 删除一个用户
userdel newuser

# 删除一个组(注意创建一个用户的同时也会创建一个组)
groupdel groupname
```
