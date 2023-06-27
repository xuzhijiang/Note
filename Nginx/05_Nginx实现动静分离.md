# 动静分离(相当于是一个伪cdn)

    静态请求直接由nginx处理,动态请求由交给tomcat处理.

# 第一种方式
    
    准备工作: mkdir /opt/www && mkdir /opt/images/
    然后在/opt/www下存放一个a.css静态文件, 在/opt/images/下存放一张图片
    
```
server {
    location /www/ {
        root /opt/;
        index index.html index.htm;
    }

    location /images/ {
        root /opt/;
        autoindex on; # 访问http://localhost/images/,会把/opt/images/ 中的内容都列出来
    }
}
```

    测试: http://localhost/images/
          http://localhost/images/1.png
          http://localhost/www/a.css

# 第2种方式

```
http {
    # 配置一个代理服务器tomcatServer1
    upstream tomcatServer1 {
        server 127.0.0.1:9090;
    }

    server {
        listen 80;
        server_name localhost;
        
        #图片静态内容(使用了正则表达式来匹配uri)
        location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$ {
            root html;
            expires 1h;
            # expires定义用户浏览器缓存的时间为7天，如果静态页面不常更新，
            # 可以设置更长，这样可以节省带宽和缓解服务器的压力
            # expires      7d; 
            # expires 1h;
        }
        
        # 所有静态文件由nginx直接处理
        location ~ .*\.(js|css)$ {
            root html;
            expires 1h;
        }

        #本地动静分离反向代理配置
        #所有jsp页面均交由tomcat处理
        location / {
            # 注意,末尾要加上封号.
            proxy_pass http://tomcatServer1;
            # index index.jsp index.html index.htm;

            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            #代理转发的请求头部增加"X-Forwarded-For"客户端地址
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
        
        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
}
```

# windows上动静分离-静态资源路径配置

![](pics/windows上动静分离路径配置01.png)
![](pics/windows上动静分离路径配置02.png)
![](pics/windows上动静分离路径配置03.png)