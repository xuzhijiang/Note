## 安装Nginx

```shell
apt-get install nginx
```

ubantu安装完Nginx后，文件结构大致为：
　　所有的配置文件都在 /etc/nginx下；
　　启动程序文件在 /usr/sbin/nginx下；
　　日志文件在 /var/log/nginx/下，分别是access.log和error.log；
　　并且在  /etc/init.d下创建了启动脚本nginx。

```shell
sudo /etc/init.d/nginx start    # 启动
sudo /etc/init.d/nginx stop     # 停止
sudo /etc/init.d/nginx restart  # 重启
sudo service nginx start
sudo service nginx stop
sudo service nginx restart
service ngnix status #查看nginx服务的状态
sudo lsof -i TCP:80 #see what application is listening on port 80
```

```shell
du -d 1 -h
sudo netstat -tlpn | grep 800 #Check that the chosen port is already in use.
mkdir -p dirname #recursively create directory.
```