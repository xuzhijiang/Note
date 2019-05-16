## ZooKeeper要求的预配置

由于ZooKeeper将数据保存在内存中以实现高吞吐量和低延迟(high throughput and low latency)，因此生产系统最适合使用8GB RAM。 较少量的RAM可能导致JVM交换，这可能导致ZooKeeper服务器延迟。 高ZooKeeper服务器延迟可能导致客户端会话超时等问题，从而对系统功能产生负面影响。

## 安装ZooKeeper软件包

把ZooKeeper Binaries下载到/opt下:

>cd /opt

从http://mirror.bit.edu.cn/apache/zookeeper/上下载,建议下载stable:

>wget http://mirror.bit.edu.cn/apache/zookeeper/stable/zookeeper-3.4.14.tar.gz

解压压缩文件:

>tar -zxvf zookeeper-3.4.14.tar.gz

Create a symbolic link using the ln command.使用`ln`命令创建一个符号链接:

>ln -s zookeeper-3.4.14 zookeeper



为zookeeper创建一个目录存放data:

>mkdir -p /data/zookeeper

