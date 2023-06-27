# 防火墙

- firewalld
- iptables

---

    CentOS中常用的防火墙程序主要是firewall和iptables，CentOS7中firewall服务已经默认安装好了，
    而iptables服务需要自己安装.(二者只能选择其中一个)

# firewall常用命令

    Firewalld是CentOS 7，Red Hat Enterprise Linux 7（RHEL 7），Fedora 18+以及其他一些流行的Linux发行版的
    默认防火墙程序,是iptables的绝佳替代品。

    配置防火墙程序后，默认情况下会阻止所有端口。 因此，即使service在服务器上的特定port上运行，
    client计算机也无法连接到该服务器。

```shell script
# 如果是从iptables切换到firewalld的话,需要先执行
systemctl unmask firewalld 
 # 启用
systemctl enable firewalld
# 检查firewalld service是否在允许
systemctl status firewalld 
# 开启firewalld
systemctl start firewalld 
# 禁用
systemctl disable firewalld
systemctl stop firewalld 

# 开启firewalld之后,就可以使用firewall-cmd
# 查看防火墙状态
firewall-cmd  --state

# 打印所有的Firewalld配置
$ sudo firewall-cmd --list-all
# The open ports and services are listed in the 
# 开放的端口和服务在 services和ports 都列了出来

# 在services中,dhcpv6-client services are enabled. 
# It means the ports corresponding to these services are also open.
# You can find out what ports these services open with the following command:
grep SERVICE_NAME /etc/services
$ grep ssh /etc/services
# 如你所看到的,ssh服务打开了tcp的22端口和udp的22端口.

# 只列出什么services有开放的端口
firewall-cmd --list-services

# 只看开放的端口(不包括services的端口)
firewall-cmd --list-ports

# 打开6379
firewall-cmd --permanent --add-port=6379/tcp

# 重新加载
firewall-cmd --reload

# 列出打开的端口(不包括services的端口)
firewall-cmd --list-ports

firewall-cmd --remove-port=6379/tcp
```

# 参考

- [https://linuxhint.com/list_open_ports_firewalld/](https://linuxhint.com/list_open_ports_firewalld/)
- [https://linuxhint.com/open-port-80-centos7/](https://linuxhint.com/open-port-80-centos7/)
- [https://www.dreamvps.com/tutorials/open-port-in-centos-7-firewalld/](https://www.dreamvps.com/tutorials/open-port-in-centos-7-firewalld/)
- [https://www.codero.com/knowledge-base/content/10/377/en/how-to-manage-firewall-rules-in-centos-7.html](https://www.codero.com/knowledge-base/content/10/377/en/how-to-manage-firewall-rules-in-centos-7.html)
- [iptables](https://man.linuxde.net/iptables)
- [iptables](https://www.rootusers.com/how-to-install-iptables-firewall-in-centos-7-linux/)
- [iptables](https://linuxhint.com/iptables_for_beginners/)
- [iptables](http://www.zsythink.net/archives/1199)