# iptables常用命令

    centos没有安装iptables,需要安装

    我们在某一个时刻只能运行firewalld或者iptables,所以使用iptables之前,要先把firewalld给彻底disable了.
    而且不能让firewalld开机启动.等切换回firewalld的时候,也要把iptables给彻底关闭了,不能开机启动.
    
    # 停止当前正在允许的firewalld
    sudo systemctl stop firewalld
    
    # Disable FirewallD
    # 禁止开机启动(注意此命令没有停止当前正在running的firewalld)
    sudo systemctl disable firewalld
    
    # 阻止其他服务启动firewalld,也包括阻止从命令行启动.
    sudo systemctl mask --now firewalld
    # 注意如果以后要从iptables切换为firewalld,需要执行
    systemctl unmask firewalld 

---

```shell script
# Install and Enable Iptables
# 默认是没有/etc/sysconfig/iptables这个文件的.安装后才有
sudo yum install iptables-services

# 使用systemctl命令查看状态
systemctl status iptables
systemctl enable iptables
systemctl disable iptables
systemctl stop iptables
systemctl restart iptables

# 或者使用service命令
service iptables status    #查看iptables状态
service iptables restart   #iptables服务重启
service iptables start
service iptables stop      #iptables服务禁用

# check the current iptables rules use the following commands(查看已添加的iptables规则):
iptables -nvL
# lists all iptables loaded rules
Iptables -L
#  the same with verbosity.
Iptables -L -v

# 将所有iptables以序号标记显示，执行：
iptables -L -n --line-numbers [-t 表名]

# -m = means “match”
```

![](pics/iptables命令语法规则01.png)

![](pics/iptables命令语法规则02.png)

![](pics/iptables命令语法规则03.png)

![](pics/iptables命令语法规则04.png)

## iptables增删改查

```shell script
# 比如要删除INPUT链里序号为8的规则，执行：
iptables -D INPUT 8  

# 让所有的主机都可以通过tcp访问80/443
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 443 -j ACCEPT

# 删除FORWARD 规则：
# 先查看FORWARD 规则
iptables -nL FORWARD --line-number
# 再删除
iptables -D FORWARD num

# 查看nat表规则
iptables -L -t nat 
iptables -t nat -L -n --line-numbers

# 要查看到nat表的防火墙规则：
iptables -t nat -L -n --line-numbers
# 如何清理掉iptables的NAT的POSTROUTING规则
# 确定你要删除的是哪一条规则，查看到了它是第几条规则，规则最前面有序号num，则删除如下：
iptables -t nat -D POSTROUTING num(改为序号)

# 其他
# 添加MASQUERADE (要测试,下面4个命令有的有问题)
iptables -t nat -A POSTROUTING -p all -m tcp -d 172.17.0.0/16 -j MASQUERADE
iptables -t nat -A POSTROUTING -s 172.17.0.0/16 -j MASQUERADE --to-source 0.0.0.0/0
iptables -t nat -A POSTROUTING -p tcp -s 172.23.0.2 -j MASQUERADE --to-source 172.23.0.2
iptables -t nat -I POSTROUTING -p tcp -s 172.23.0.2 --dport 23000 -j MASQUERADE --to-source 172.23.0.2

# 参考:http://blog.51yip.com/linux/1404.html
```

![](pics/让所有的主机都可以通过tcp访问80和443.png)

```shell script
# 我们告诉Iptables可以接收协议是TCP,访问端口80或443的流量

# 我们可以通过添加参数“-s”来更改命令以仅接受来自特定IP的连接：
iptables -A INPUT -s 127.0.0.1 -p tcp --dport 80 -j ACCEPT

# 现在,在结尾，让我们拒绝所有未接受的连接：
iptables -P OUTPUT ACCEPT
iptables -P INPUT DROP

# 其中-P用来定义规则链(如INPUT)的默认目标(或者说动作)，也就是这个规则链的默认策略，ACCEPT，DROP，QUEUE或RETURN。
# 在这种情况下，我们说OUTPUT的默认策略是接受，INPUT的默认策略是拒绝，除非我们在之前的规则中指定了不同的内容。这是一个非常基本的防火墙，它不包括许多攻击的规则，用于学习目的而不是用于生产.
```

```shell script
# 方式2: 永久添加iptable rule
vi /etc/sysconfig/iptables
```

# iptables详解

![](pics/iptables01.png)
![](pics/iptables02.png)
![](pics/iptables03.png)
![](pics/iptables04.png)

    我们一般只关注INPUT链和OUTPUT链.

![](pics/iptables05.png)
![](pics/iptables06.png)
![](pics/iptables07.png)
![](pics/iptables08.png)
![](pics/iptables09.png)
![](pics/iptables10.png)
![](pics/iptables11.png)

# iptabels实际操作之规则查询

```shell script
# 查看对应表的所有规则,省略-t 表名的时候表示操作的是filter表.
iptables -t 表名 -v -L

# 查看指定表的指定链中的规则
iptables -t 表名 -L 链名

# 查看对应表的所有规则,并在显示规则的时候,不对规则中的ip进行名称的反解析(显示的地址为0.0.0.0/0),-n选项表示不解析ip地址.
iptables -t 表名 -n -L

# 只查看某张表中的某条链
iptables -t filter -nvL INPUT
```

![](pics/iptables-filter测试.png)
![](pics/iptables-filter测试02.png)
![](pics/iptables-filter测试03.png)
![](pics/iptables-filter测试04.png)
![](pics/iptables-filter测试05.png)
![](pics/iptables-filter测试06.png)

# 参考

- [https://linuxhint.com/list_open_ports_firewalld/](https://linuxhint.com/list_open_ports_firewalld/)
- [https://linuxhint.com/open-port-80-centos7/](https://linuxhint.com/open-port-80-centos7/)
- [https://www.dreamvps.com/tutorials/open-port-in-centos-7-firewalld/](https://www.dreamvps.com/tutorials/open-port-in-centos-7-firewalld/)
- [https://www.codero.com/knowledge-base/content/10/377/en/how-to-manage-firewall-rules-in-centos-7.html](https://www.codero.com/knowledge-base/content/10/377/en/how-to-manage-firewall-rules-in-centos-7.html)
- [iptables](https://man.linuxde.net/iptables)
- [iptables](https://www.rootusers.com/how-to-install-iptables-firewall-in-centos-7-linux/)
- [iptables](https://linuxhint.com/iptables_for_beginners/)
- [iptables](http://www.zsythink.net/archives/1199)
- [参考](http://www.zsythink.net/archives/1493)
- [参考](http://www.zsythink.net/archives/1517)