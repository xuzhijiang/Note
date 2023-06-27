# 将项目web-admin.zip容器化部署项目到tomcat

```shell script
docker search tomcat
# 这里我们拉取官方的镜像
docker pull tomcat

# 拷贝web-admin.zip到/usr/local/docker/tomcat/ROOT
cp web-admin.zip /usr/local/docker/tomcat/ROOT && cd /usr/local/docker/tomcat/ROOT
# 解压zip,这时ROOT下会出现3个文件夹,static,WEB-INF,META-INF
unzip web-admin.zip
# 然后把zip就可以删除了
rm web-admin.zip
# 切换到/usr/local/docker/tomcat
cd /usr/local/docker/tomcat
# 启动tomcat容器(启动之前要修改web-admin.zip中的mysql数据源等)
docker run -p 8080:8080 --name myshop -v /usr/local/docker/tomcat/ROOT:/usr/local/tomcat/webapps/ROOT -d tomcat
```