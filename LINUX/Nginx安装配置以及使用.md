### 安装Nginx

1. 所有的配置文件都在 /etc/nginx下；
2. 启动程序文件在 /usr/sbin/nginx下；
3. 日志文件在 /var/log/nginx/下，分别是access.log和error.log；
4. 并且在/etc/init.d下创建了启动脚本nginx。

```shell
# linux默认是不能访问8080端口的，所以要手动打开
# 如何打开某个指定端口

# 打开后，重启防火墙
service iptables restart

sudo /etc/init.d/nginx start    # 启动
sudo /etc/init.d/nginx stop     # 停止
sudo /etc/init.d/nginx restart  # 重启
sudo service nginx start
sudo service nginx stop
sudo service nginx restart
service ngnix status #查看nginx服务的状态

# 关闭防火墙
service iptables stop
```