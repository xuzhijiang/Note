![](pics/Metricbeat01.png)

![](pics/Metricbeat02.png)

[下载安装](https://www.elastic.co/downloads/beats/metricbeat)

![](pics/Metricbeat03.png)

![](pics/Metricbeat04.png)

```shell script
./metricbeat modules list

./configure --prefix=/usr/local/nginx --with-http_stub_status_module
make && make install

./nginx -V
vim conf/nginx.conf

location /nginx-status {
        stub_status on;
        access_log off;
}

./nginx -s reload
./nginx -s quit
./nginx -c nginx.conf

# 访问
http://192.168.32.128/nginx-status
```

![](pics/Metricbeat05.png)

![](pics/Metricbeat06.png)

```shell script
./metricbeat modules enable nginx
```

![](pics/Metricbeat07.png)

![](pics/Metricbeat08.png)

![](pics/Metricbeat09.png)

