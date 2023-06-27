# 什么是正向代理？(vpn)

    在客户端配置代理服务器,然后通过代理服务器访问谷歌,这个就叫正向代理.
    
    正向代理的特点: 客户端可以明确的知道代理服务器的存在.

# 什么是反向代理？

    客户端对代理无感知,表现就是一台负责转发请求的代理服务器，充当了真正服务器的功能

# 反向代理-示例1:

    访问nginx的80端口,代理到tomcat的8081端口

```shell script
http {
    server {
            listen       80;
            server_name  localhost;
    
            location / {
                proxy_pass http://127.0.0.1:8081;
                index  index.html index.htm;
            }
    }
}
```

# 反向代理-示例2:

    根据访问的路径跳转到不同端口的服务中:
    如果访问路径中包含edu就转发到8081,如果包含了vod就转发到8082.
    访问 http://192.168.17.129/edu/a.html 直接跳转到 127.0.0.1:8081 (在webapps/edu/下准备a.html页面)
    访问 http:// 192.168.17.129/vod/a.html 直接跳转到 127.0.0.1:8082 (在webapps/vod/下准备a.html页面)

    第1种方式

```shell script
http {
    server {
        location / {
            # 欢迎页面，按照从左到右的顺序查找页面
            index index.jsp index.html index.htm;
        }

        # ~: 用于表示 uri 包含正则表达式(也就是uri中包含/edu/这个路径,直接转发到8081)，并且区分大小写
        location ~ /edu/  {
            proxy_pass http://127.0.0.1:8081;
        }    
        
        location ~ /vod/  {
            proxy_pass http://127.0.0.1:8082;
        } 
    }
}
```

    第二种方式

```shell script
http {
    upstream tomcatServer1 {
        server 127.0.0.1:8081;
    }
    upstream tomcatServer2 {
        server 127.0.0.1:8082;
    }

    # 配置一个虚拟主机
    server {
        listen 80;
        server_name localhost;
        location / {
            index index.jsp index.html index.htm;
        }

        # ~: 用于表示 uri 包含正则表达式(也就是uri中包含/edu/这个路径,直接转发到8081)，并且区分大小写
        location ~ /edu/  {
            proxy_pass http://tomcatServer1;
        }    
        location ~ /vod/  {
            proxy_pass http://tomcatServer2;
        } 
    }
}
```
