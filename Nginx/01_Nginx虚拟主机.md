# Nginx 虚拟主机

## 什么是虚拟主机？

它可以将一台计算机分成多个虚拟主机，每个虚拟主机可以独立对外提供 www 服务，这样就可以实现一台主机对外提供多个 web服务，每个虚拟主机之间是独立的，互不影响的。

通过 Nginx 可以实现虚拟主机的配置，Nginx 支持三种类型的虚拟主机配置

- 基于不同IP的虚拟主机
- 基于域名的虚拟主机
- 基于端口的虚拟主机

## conf目录下的 nginx.conf配置文件的结构

- main块: 配置影响nginx全局的指令。一般有运行nginx服务器的用户组，nginx进程pid存放路径，日志存放路径，worker process数(work进程的个数)等。
- events块: 可配置worker_connections-每个worker能够并发响应的最大请求数
- http块: 可以嵌套多个server
- upstream块: 配置HTTP负载均衡器分配流量到几个应用程序服务器。
- server: 配置虚拟主机的相关参数，一个http中可以有多个server。
- location块: 配置请求的路由，以及允许根据用户请求的URI来匹配指定的各location以进行访问配置

详细的配置可以参考: [](https://blog.csdn.net/tjiyu/article/details/53027619)

```
# 注： 每个 server 就是一个虚拟主机

... #main全局块

#events块
events {}

#http块
http {
    #http全局块

    upstream … # upstream负载均衡块
    {
        ...
    }

    server #server块
    {
        ... #server全局块

        #location块
        location [PATTERN] 
        {
            ...
        }

        location [PATTERN]
        {
            ...
        }
    }

    server
    {
        ...
    }

    ... #http全局块
}
```

### 基于不同端口的虚拟主机配置

- Nginx 对外提供 80 和 8080 两个端口监听服务
- 请求 80 端口则请求 html80 目录下的 html
- 请求 8080 端口则请求 html8080 目录下的 html

>创建目录及文件

在`/usr/local/docker/nginx/wwwroot`目录下创建 html80 和 html8080 两个目录，并分辨创建两个 index.html 文件

>配置虚拟主机

修改 /usr/local/docker/nginx/conf 目录下的 nginx.conf 配置文件：

```shell
# 启动进程,通常设置成和 CPU 的数量相等
worker_processes  1;

events {
    # epoll 是多路复用 IO(I/O Multiplexing) 中的一种方式
    # 但是仅用于 linux2.6 以上内核,可以大大提高 nginx 的性能
    use epoll;
    # 单个后台 worker process 进程的最大并发链接数
    worker_connections  1024;
}

http {
    # 设定 mime 类型,类型由 mime.type 文件定义
    include       mime.types;
    default_type  application/octet-stream;

    # sendfile 指令指定 nginx 是否调用 sendfile 函数（zero copy 方式）来输出文件，对于普通应用，
    # 必须设为 on，如果用来进行下载等应用磁盘 IO 重负载应用，可设置为 off，以平衡磁盘与网络 I/O 处理速度，降低系统的 uptime.
    sendfile        on;
    
    # 连接超时时间
    keepalive_timeout  65;

    # 设定请求缓冲
    client_header_buffer_size 2k;

    # 配置虚拟主机 192.168.75.145
    server {
        # 监听的ip和端口，配置 192.168.75.145:80
        listen       80;
        # 虚拟主机名称,这里配置ip地址
        server_name  192.168.75.145;
        # 所有的请求都以 / 开始，所有的请求都可以匹配此 location
        location / {
            # 使用 root 指令指定虚拟主机目录,即网页存放目录
            # 比如访问 http://ip/index.html 将找到 /usr/local/docker/nginx/wwwroot/html80/index.html
            # 比如访问 http://ip/item/index.html 将找到 /usr/local/docker/nginx/wwwroot/html80/item/index.html

            root   /usr/share/nginx/wwwroot/html80;

            # 指定欢迎页面，按从左到右顺序查找
            index  index.html index.htm;
        }

    }

    # 配置虚拟主机 192.168.75.145
    server {
        listen       8080;
        server_name  192.168.75.145;

        location / {
            root   /usr/share/nginx/wwwroot/html8080;
            index  index.html index.htm;
        }
    }
}
```

## 基于不同域名的虚拟主机配置

- 两个域名指向同一台 Nginx 服务器，用户访问不同的域名显示不同的网页内容
- 两个域名是`admin.service.itoken.funtl.com` 和 `admin.web.itoken.funtl.com`
- Nginx 服务器使用虚拟机 192.168.75.145

>配置 Windows Hosts 文件

- 通过 host 文件指定 admin.service.itoken.funtl.com 和 admin.web.itoken.funtl.com 对应 192.168.75.145 虚拟机
- 修改 window 的 hosts 文件：（C:\Windows\System32\drivers\etc）

>创建目录及文件

在 /usr/local/docker/nginx/wwwroot 目录下创建 htmlservice 和 htmlweb 两个目录，并分辨创建两个 index.html 文件

>配置虚拟主机

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
        server_name  admin.service.itoken.funtl.com;
        location / {
            root   /usr/share/nginx/wwwroot/htmlservice;
            index  index.html index.htm;
        }

    }

    server {
        listen       80;
        server_name  admin.web.itoken.funtl.com;

        location / {
            root   /usr/share/nginx/wwwroot/htmlweb;
            index  index.html index.htm;
        }
    }
}
```
