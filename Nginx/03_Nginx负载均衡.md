# Nginx 负载均衡(软件上的负载均衡)

负载均衡，英文名称为 Load Balance，意思是把请求分摊到多个服务器处理.

# Nginx 实现负载均衡

- nginx 负载均衡服务器：192.168.75.145:80
- tomcat1 服务器：192.168.75.145:9090
- tomcat2 服务器：192.168.75.145:9091

# Nginx 配置负载均衡

    修改 /usr/local/docker/nginx/conf 目录下的 nginx.conf 配置文件：

```shell
user  nginx;
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;

    keepalive_timeout  65;
    
    # 定义名为myapp1的负载均衡组，在下面引用
    upstream myapp1 {
        # 权重为10
        server 192.168.75.145:9090 weight=10;
        server 192.168.75.145:9091 weight=10;
    }

    server {
        listen 80;
        server_name nginx.funtl.com;
        location / {
            proxy_pass http://myapp1;
            index index.jsp index.html index.htm;
        }
    }
}
```

# 相关配置说明

```shell
# 定义负载均衡设备的 Ip及设备状态 
upstream myServer {
    server 127.0.0.1:9090 down;
    server 127.0.0.1:8080 weight=2;
    server 127.0.0.1:6060;
    server 127.0.0.1:7070 backup;
}
```

>在需要使用负载的 Server 节点下添加: `proxy_pass http://myServer;`

- upstream：每个设备的状态:
- down：表示当前的 server 暂时不参与负载
- weight：默认为1, weight 越大，负载的权重就越大。
- max_fails：允许请求失败的次数默认为 1 当超过最大次数时，返回 proxy_next_upstream 模块定义的错误
- fail_timeout:max_fails 次失败后，暂停的时间。
- backup：其它所有的非 backup 机器 down 或者忙的时候，请求 backup 机器。所以这台机器压力会最轻
