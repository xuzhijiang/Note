```shell
docker search tomcat

# 这里我们拉取官方的镜像
docker pull tomcat

# 运行容器：
docker run --name tomcat -p 8080:8080 -v $PWD/test:/usr/local/tomcat/webapps/test -d tomcat

# 命令说明：
# -p 8080:8080：将容器的8080端口映射到主机的8080端口
# -v $PWD/test:/usr/local/tomcat/webapps/test：将主机中当前目录下的test挂载到容器的/test

# 查看容器启动情况
docker ps

# 通过浏览器访问验证
```