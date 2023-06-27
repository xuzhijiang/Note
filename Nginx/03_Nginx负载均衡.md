# 负载均衡策略

    第一种 轮询（默认方式）
    每个请求按时间顺序逐一分配到不同的后端服务器，如果后端服务器 down 掉，能自动剔除。
    
    第三种 ip_hash (根据ip的哈希结果来分配)
    每个请求按客户端IP的 hash 结果分配，这样保证了 相同的客户端的请求一直发送到相同的服务器,可以解决session的问题,
    当有服务器需要剔除，必须手动down掉
    
    第4种: least_conn(最少连接策略)
    把请求转发给连接数较少的后端服务器。轮询算法是把请求平均的转发给各个后端，使它们的负载大致相同；
    但是，有些请求占用的时间很长，会导致其所在的后端负载较高。这种情况下，least_conn这种方式就可以达到更好的负载均衡效果。
    此负载均衡策略适合请求处理时间长短不一造成服务器过载的情况
    
    第5种 fair(依据响应时间)
    (第三方策略,第三方的负载均衡策略的实现需要安装第三方插件)
    按后端服务器的响应时间来分配请求，响应时间短的优先分配。

    第6种: url_hash(第三方,依据URL的哈希值来分配)
    按访问url的hash结果来分配请求，使每个url定向到同一个后端服务器，要配合缓存命中来使用。
    同一个资源多次请求，可能会到达不同的服务器上，导致不必要的多次下载，缓存命中率不高，以及一些资源时间的浪费。
    而使用url_hash，可以使得同一个url（也就是同一个资源请求）会到达同一台服务器，一旦缓存住了资源，
    再此收到请求，就可以从缓存中读取。　

# 示例

    浏览器地址栏输入地址 http://192.168.17.129/edu/a.html，负载均衡效果，平均到 8081和 8082 端口中

# 第一种 轮询（默认）     

```
http {
    # 定义名为myserver的负载均衡组
    upstream myserver {
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
    }

    server {
        listen 80;
        server_name localhost;
        location / {
            proxy_pass http://myserver;
            root html;
            index index.jsp index.html index.htm;
        }
    }
}
```

# 第三种 ip_hash

```
http {
    # 定义名为myserver的负载均衡组
    upstream myserver {
        ip_hash;
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
    }

    server {
        listen 80;
        server_name localhost;
        location / {
            proxy_pass http://myserver;
            root html
            index index.jsp index.html index.htm;
        }
    }
}
```    

# 第4种: least_conn

```
http {
    # 定义名为myserver的负载均衡组
    upstream myserver {
        least_conn; #把请求转发给连接数较少的后端服务器
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
    }

    server {
        listen 80;
        server_name localhost;
        location / {
            proxy_pass http://myserver;
            root html
            index index.jsp index.html index.htm;
        }
    }
}
```      

# 第5种 fair(需要安装第三方插件)

```
http {
    # 定义名为myserver的负载均衡组
    upstream myserver {
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
        fair; #实现响应时间短的优先分配
    }

    server {
        listen 80;
        server_name localhost;
        location / {
            proxy_pass http://myserver;
            root html
            index index.jsp index.html index.htm;
        }
    }
}
```        

# 第6种 url_hash(需要安装第三方插件)

```
http {
    # 定义名为myserver的负载均衡组
    upstream myserver {
        hash $request_uri;    #实现每个url定向到同一个后端服务器
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
    }

    server {
        listen 80;
        server_name localhost;
        location / {
            proxy_pass http://myserver;
            root html
            index index.jsp index.html index.htm;
        }
    }
}
```  