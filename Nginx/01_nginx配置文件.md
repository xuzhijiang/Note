# nginx的配置文件由哪几部分组成?

    全局块+events块+http块
    
    全局块: 从配置文件开始到 events 块之间的内容.影响 nginx 服务器整体运行的配置都在这里.
    nginx进程pid存放路径，日志存放路径，worker process数(work进程的个数)等
    
    events块: 可配置worker_connections(每个worker进程支持的最大连接数)等.,主要影响 Nginx 服务器与用户的网络连接.

    http块: 包括了 http 全局块、server 块(虚拟主机).upstream块(和负载均衡有关)

# nginx.conf详细说明

```
#全局块
# 配置运行nginx的用户或组,默认为nobody
# 运行用户
user  nginx;

# worker进程数
worker_processes  1;

# 全局错误日志以及pid文件
#error_log  logs/error.log;
#pid        logs/nginx.pid;

events {
    # use epoll; # epoll是io多路复用中的一种方式,但是仅用于linux2.6以上的内核,可以大大提搞nginx的性能
    worker_connections  1024; # 一个worker process进程的最大连接数
}

http {
    # 设定mime类型,mime.types是conf目录下的一个文件,里面包含了mime类型 (nginx支持对哪些文件的访问和处理)
    # 注意: 不支持jsp
    include       mime.types;
    # 
    default_type  application/octet-stream;
    # sendfile 指令指定 nginx 是否调用 sendfile 函数（zero copy 方式）来输出文件，对于普通应用，
    # 必须设为 on，如果用来进行下载等应用磁盘 IO 重负载应用，可设置为 off，以平衡磁盘与网络 I/O 处理速度，降低系统的 uptime.
    sendfile        on;
    # 连接超时时间
    keepalive_timeout  65;
    
    # 开启gzip压缩
    #gzip  on;
    
    # 设定请求缓冲
    client_header_buffer_size 2k;

    # 定义upstream负载均衡组
    # upstream：新版 Nginx 的 upstream 配置中的名称不可以有下划线("_")，否则会报 400 错误
    # down：表示当前的 server 暂时不参与负载
    # weight：默认为1, weight 越大，负载的权重就越大。
    # max_fails：允许请求失败的次数默认为 1 当超过最大次数时，返回 proxy_next_upstream 模块定义的错误
    # fail_timeout:max_fails 次失败后，暂停的时间。
    # backup：其它所有的非 backup 机器 down 或者忙的时候，请求 backup 机器。所以这台机器压力会最轻
    upstream myServer {
        #ip_hash; #ip_hash负载均衡策略
        # least_conn; #最少连接负载均衡策略
        # least_time last_byte; #最少响应时间策略,商业版本
        # server 192.168.18.252:8080 weight=1 max_fails=2 fail_timeout=2; 
        server 127.0.0.1:9090 down;
        server 127.0.0.1:8080 weight=2;
        server 127.0.0.1:6060;
        # 指定代理服务器自身作为备份server，当所有后端服务器都宕机时，对外提供维护提示页面
        # 注意，当下面代理服务器为backup时，当后端服务器重新上线时，不能进行正常转发
        server 127.0.0.1:7070 backup; 
    }

    # 定义名为myapp1的负载均衡组，在下面引用
    upstream myapp1 {
        # 权重为10
        server 192.168.75.145:9090 weight=10;
        server 192.168.75.145:9091 weight=10;
    }

    # 每一个server就是一个虚拟主机
    server {
        # 监听的ip和端口
        listen 80;

        # 虚拟主机名称,这里配置ip地址,可以是基于域名的
        server_name nginx.funtl.com;

        # 据用户请求的URI来匹配指定的各location
        location / { # 默认请求
            proxy_pass http://myapp1;
            # 定义服务器的默认网站根目录位置
            # 使用 root 指令指定虚拟主机目录,即网页存放目录
            # root /usr/share/nginx/wwwroot/html80;
            # 比如访问 http://ip/index.html 将找到 /usr/share/nginx/wwwroot/html80/index.html
            # 比如访问 http://ip/item/index.html 将找到 /usr/share/nginx/wwwroot/html80/item/index.html
            # root html # 这个html默认就是nginx安装目录下面的html文件夹,也就是会去nginx安装目录下面html文件夹中找文件

            # 欢迎页面，按照从左到右的顺序查找页面
            index index.jsp index.html index.htm;
        }

        location /www/ {
                # url访问: http://94.191.29.122/www/aa.css
                # 对应磁盘路径: /opt/www/aa.css
                root /opt/;
                # 在/opt/文件夹下面,默认加载index.html文件,如果index.html文件不存在,那么就加载index.htm
                index index.html index.htm;
                autoindex on;
        }

        location /images/ {
                # url访问: http://94.191.29.122/images/1.png
                # 对应磁盘路径: /opt/images/1.png
                root /opt/;
                autoindex on;
        }

        # 建立下载站点下载列表
        location /download/ {
             # 把html目录下的download目录下的文件暴露到公网
             # 打开下载列表功能
             autoindex on;
             # 显示时间
             autoindex_localtime on;
        }

        # 404错误重定向到哪个页面
        #error_page  404              /404.html;
        
        # redirect server error pages to the static page /50x.html
        # 错误码为500 502 503 504重定向到哪个页面
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
        
        location /NginxStatus {
            stub_status on;
            access_log  on;
            auth_basic  "NginxStatus";
            auth_basic_user_file conf/htpasswd;
        }

        # 禁止访问 .htxxx  文件
        location ~ /\.ht {
            deny all;
        }
    }
}
```

# 多个虚拟主机配置

    Nginx 支持三种类型的虚拟主机配置

- 基于不同IP的虚拟主机(不怎么用)
- 基于域名domain的虚拟主机
- 基于端口port的虚拟主机

## 基于不同port的虚拟主机配置

- Nginx 对外提供 80 和 8080 两个端口监听服务
- 请求 80 端口则请求 html80 目录下的 html
- 请求 8080 端口则请求 html8080 目录下的 html

## 基于不同domain的虚拟主机配置

- 两个域名指向同一台 Nginx 服务器，用户访问不同的域名显示不同的网页内容
- 两个域名是admin.aaa.com和item.aaa.com
- Nginx 服务器使用虚拟机 192.168.75.145


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

    server {
        listen       80;
        server_name  admin.aaa.com;
        location / {
            root   /usr/share/nginx/wwwroot/htmlservice;
            index  index.html index.htm;
        }

    }

    server {
        listen       80;
        server_name  item.aaa.com;

        location / {
            root   /usr/share/nginx/wwwroot/htmlweb;
            index  index.html index.htm;
        }
    }
}
```
