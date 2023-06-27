# 为什么要配置nginx的高可用

    nginx可能会宕机,nginx宕机之后,就没法转发请求到tomcat了,所以也要配置nginx的高可用.

![](pics/只有一个nginx会有什么问题.png)

    master宕机之后,就自动切换到backup服务器,就可以达到nginx高可用的效果.

    高可用 用到了一个软件,叫keepalived的服务,这个服务 通过脚本检查当前nginx是否还活着
    然后client对外通过 虚拟ip 访问.

    keepalived把 虚拟ip 绑定到master的某一个网卡上, master挂了之后,keepalived服务会把虚拟ip重新 绑定到backup服务器的网卡上.

#  keepalived.conf(master)

```shell script
global_defs { # 全局定义
    notification_email { # 通知邮件
        acassen@firewall.loc
        failover@firewall.loc
        sysadmin@firewall.loc
    }
    notification_email_from Alexandre.Cassen@firewall.loc
    smtp_server 192.168.32.129
    smtp_connect_timeout 30
    router_id LVS_DEVEL # 访问的主机(通过修改/etc/hosts,添加127.0.0.1 到 LVS_DEVEL的映射)
}

vrrp_script chk_http_port {
    # 检测脚本,keepalived它要知道nginx目前是否还活着,如果还活着就可以继续访问,如果没有活着,就切换到备份服务器.
    # 这就需要一个检测脚本.
    script "/usr/local/src/nginx_check.sh" 
    interval 2 #（检测脚本执行的间隔,这里就是每隔2秒脚本执行一次）
    weight 2 # 表示权重
}

vrrp_instance VI_1 { # 虚拟ip的配置
    state MASTER # 如果是主服务器,这里就是MASTER,如果是备份服务器 就是 BACKUP
    interface ens33 # 你要在你的哪个网卡上绑定 虚拟主机的虚拟ip
    virtual_router_id 51 # 主、备机的 virtual_router_id(相当于是一个唯一标识), 主备必须相同
    priority 100 # 主、备机取不同的优先级(用来表示你当前是主还是备份服务器)，主机值较大，备份机值较小,一般master设置为100,backup设置为比100小.
    advert_int 1 # 时间间隔,表示每隔多长时间发送一个心跳,就是检测你这个服务器是否还活着.默认是每隔1秒发送一个心跳.
    authentication { # 一种校验权限的方式
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
      192.168.32.50 # 虚拟ip地址,当然可以绑定多个虚拟ip
    }
}
```

#  keepalived.conf(backup)

```shell script
global_defs {
    notification_email {
        acassen@firewall.loc
        failover@firewall.loc
        sysadmin@firewall.loc
    }
    notification_email_from Alexandre.Cassen@firewall.loc
    smtp_server 192.168.32.129
    smtp_connect_timeout 30
    router_id LVS_DEVEL
}

vrrp_script chk_http_port {
    script "/usr/local/src/nginx_check.sh" 
    interval 2
    weight 2
}

vrrp_instance VI_1 {
    state BACKUP  
    interface ens33
    virtual_router_id 51
    priority 90
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
      192.168.32.50
    }
}
```

# 检测脚本

```shell script
# nginx_check.sh
#!/bin/bash
A=`ps -C nginx - no-header |wc -l`
if [ $A -eq 0 ];then
    /usr/local/nginx/sbin/nginx # nginx的启动路径
    sleep 2
    if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then # 判断nginx是否还活着,如果还活着就可以继续访问,否则就切换到另外一个nginx
        killall keepalived
    fi
fi
```

# 启动

```shell script
# 在master上第一步: 启动nginx
# 在master上第2步: 启动keepalived
systemctl start keepalived.service

# 在backup上第一步: 启动nginx
# 在backup上第2步: 启动keepalived
systemctl start keepalived.service

# 在master上查看虚拟ip是否绑定成功
ip a
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    inet 192.168.32.50/32 scope global ens33 # 表示ens33这个网卡 绑定到了 192.168.32.50
       valid_lft forever preferred_lft forever

# 访问: http://192.168.32.50/

# 然后把主服务器（192.168.32.128）的 nginx 和 keepalived 都停止，再输入 192.168.32.50,看是否还可以访问
systemctl stop keepalived.service
./nginx -s stop

# 在从服务器上
ip a
    inet 192.168.32.50/32 scope global ens33
       valid_lft forever preferred_lft forever # 表示ens33这个网卡 绑定到了 192.168.32.50
```