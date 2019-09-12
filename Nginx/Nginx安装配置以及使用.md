# 安装Nginx

参考链接: 
- https://docs.nginx.com/nginx/admin-guide/installing-nginx/installing-nginx-open-source/
- https://www.thegeekstuff.com/2011/07/install-nginx-from-source/
- https://blog.csdn.net/tjiyu/article/details/53027881

```shell
groupadd -r nginx

# 创建nginx user 到nginx组
useradd -r -g nginx nginx

usermod -s /sbin/nologin nginx

wget http://nginx.org/download/nginx-1.0.5.tar.gz

tar -xvfz nginx-1.0.5.tar.gz

cd nginx-1.0.5

./configure --help(可能提示缺少库)

# 我们试图启用几乎所有模块。 您也可以通过从此列表中删除任何模块来删除它。
./configure --user=nginx --group=nginx \ 
	--sbin-path=/usr/sbin/nginx \ 
	--conf-path=/etc/nginx/nginx.conf \ 
	--error-log-path=/var/log/nginx/error.log \ 
	--http-log-path=/var/log/nginx/access.log \ 
	--with-rtsig_module --with-select_module --with-poll_module --with-file-aio --with-ipv6 --with-http_ssl_module --with-http_spdy_module --with-http_realip_module --with-http_addition_module --with-http_xslt_module --with-http_image_filter_module --with-http_geoip_module --with-http_sub_module --with-http_dav_module --with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_auth_request_module --with-http_random_index_module --with-http_secure_link_module --with-http_degradation_module --with-http_stub_status_module --with-http_perl_module --with-mail --with-mail_ssl_module --with-cpp_test_module  --with-cpu-opt=CPU --with-pcre  --with-pcre-jit  --with-md5-asm  --with-sha1-asm  --with-zlib-asm=CPU --with-libatomic --with-debug --with-ld-opt="-Wl,-E"

# 上面这步我不用直接这样,使用默认配置
./configure --user=nginx --group=nginx

# 可能需要安装
yum install pcre-devel
yum install zlib-devel

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

sudo /etc/init.d/nginx start    # 启动
sudo /etc/init.d/nginx stop     # 停止
sudo /etc/init.d/nginx restart  # 重启

sudo service nginx start
sudo service nginx stop
sudo service nginx restart
service ngnix status #查看nginx服务的状态
```
