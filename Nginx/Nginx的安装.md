# 安装Nginx

```shell script
cd ~/ && wget http://nginx.org/download/nginx-1.0.5.tar.gz
tar -xvfz nginx-1.0.5.tar.gz
cd nginx-1.0.5
# 要提前创建好这个文件夹
mkdir /usr/local/nginx

# 直接使用默认配置
./configure --user=nginx --group=nginx
make & make install
# nginx被安装到了/usr/local/nginx/sbin中

# 常用选项
# -c </path/to/config> 为 Nginx 指定一个配置文件，来代替缺省的。
/usr/sbin/nginx -c /etc/nginx/nginx.conf

# 不运行，而仅仅测试配置文件。nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。
-t 
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

# nginx安装详细配置

```shell script
./configure --user=nginx --group=nginx \ 
	--sbin-path=/usr/sbin/nginx \ 
	--conf-path=/etc/nginx/nginx.conf \ 
	--error-log-path=/var/log/nginx/error.log \ 
	--http-log-path=/var/log/nginx/access.log \ 
	--with-rtsig_module --with-select_module --with-poll_module --with-file-aio --with-ipv6 --with-http_ssl_module --with-http_spdy_module --with-http_realip_module --with-http_addition_module --with-http_xslt_module --with-http_image_filter_module --with-http_geoip_module --with-http_sub_module --with-http_dav_module --with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_auth_request_module --with-http_random_index_module --with-http_secure_link_module --with-http_degradation_module --with-http_stub_status_module --with-http_perl_module --with-mail --with-mail_ssl_module --with-cpp_test_module  --with-cpu-opt=CPU --with-pcre  --with-pcre-jit  --with-md5-asm  --with-sha1-asm  --with-zlib-asm=CPU --with-libatomic --with-debug --with-ld-opt="-Wl,-E"
```
