# 基于 Docker 安装 Nexus

```shell script
# 下载nexus3代
docker pull sonatype/nexus3
cd /usr/local/docker && mkdir nexus && cd nexus
vim docker-compose.yml
docker-compose up -d
```

我们使用 Docker 来安装和运行 Nexus，docker-compose.yml 配置如下：

```yaml
version: '3.1'
services:
  nexus:
    restart: always
    image: sonatype/nexus3
    container_name: nexus
    ports:
      - 8081:8081
    volumes:
      - /usr/local/docker/nexus/data:/nexus-data
```

>注：如果无法访问的话,要通过`docker logs id/name`查看信息,启动时如果出现权限问题可以使用：chmod 777 /usr/local/docker/nexus/data 赋予数据卷目录可读可写的权限

# 登录控制台验证安装

    地址：http://ip:port/ 用户名：admin 密码：admin123
    