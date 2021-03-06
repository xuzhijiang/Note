# 直接安装Nginx

- https://docs.nginx.com/nginx/admin-guide/installing-nginx/installing-nginx-open-source/
- https://www.thegeekstuff.com/2011/07/install-nginx-from-source/
- https://blog.csdn.net/tjiyu/article/details/53027881

```shell
groupadd -r nginx

# 创建nginx user 到nginx组
useradd -r -g nginx nginx

usermod -s /sbin/nologin nginx

cd ~/
wget http://nginx.org/download/nginx-1.0.5.tar.gz
tar -xvfz nginx-1.0.5.tar.gz

cd nginx-1.0.5
# 要提前创建好这个文件夹
mkdir /usr/local/nginx

./configure --help

# 直接使用默认配置
./configure --user=nginx --group=nginx

make & make install

# nginx被安装到了/usr/local/nginx/sbin中

# nginx命令行参数
./nginx –h

# 常用选项
# -c </path/to/config> 为 Nginx 指定一个配置文件，来代替缺省的。
/usr/sbin/nginx -c /etc/nginx/nginx.conf

# 不运行，而仅仅测试配置文件。nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。
-t 

# 显示 nginx 的版本，编译器版本和配置参数。
-V 

# 启动nginx
./nginx
# 一旦启动后,通过ps,你将会看到master进程和worker进程.
ps -ef | grep -i nginx

# 停止nginx
./nginx -s quit 优雅的停止(or stop)

# 重新加载配置文件
./nginx -s reload

sudo /etc/init.d/nginx start    # 启动
sudo /etc/init.d/nginx stop     # 停止
sudo /etc/init.d/nginx restart  # 重启

sudo service nginx start
sudo service nginx stop
sudo service nginx restart
service ngnix status #查看nginx服务的状态
```

# 安装可能的错误

```shell script
# 1. install the PCRE library into the system
# centos下解决
yum install pcre-devel
# ubuntu下解决
apt-get install libpcre3 libpcre3-dev

# 2. install the zlib library
# centos下解决
yum install zlib-devel
# ubuntu下解决
apt-get install zlib1g-dev
```

# nginx的配置

```shell script
./configure --user=nginx --group=nginx \ 
	--sbin-path=/usr/sbin/nginx \ 
	--conf-path=/etc/nginx/nginx.conf \ 
	--error-log-path=/var/log/nginx/error.log \ 
	--http-log-path=/var/log/nginx/access.log \ 
	--with-rtsig_module --with-select_module --with-poll_module --with-file-aio --with-ipv6 --with-http_ssl_module --with-http_spdy_module --with-http_realip_module --with-http_addition_module --with-http_xslt_module --with-http_image_filter_module --with-http_geoip_module --with-http_sub_module --with-http_dav_module --with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_auth_request_module --with-http_random_index_module --with-http_secure_link_module --with-http_degradation_module --with-http_stub_status_module --with-http_perl_module --with-mail --with-mail_ssl_module --with-cpp_test_module  --with-cpu-opt=CPU --with-pcre  --with-pcre-jit  --with-md5-asm  --with-sha1-asm  --with-zlib-asm=CPU --with-libatomic --with-debug --with-ld-opt="-Wl,-E"
```

# Docker 来安装和运行 Nginx

docker-compose.yml 配置如下：

```yaml
version: '3.1'
services:
  nginx:
    restart: always
    image: nginx
    container_name: nginx
    ports:
      - 81:80
    volumes:
      # 点代表当前yml的所在的目录,左边是宿主机目录
      - ./conf/nginx.conf:/etc/nginx/nginx.conf
      - ./wwwroot:/usr/share/nginx/wwwroot
```