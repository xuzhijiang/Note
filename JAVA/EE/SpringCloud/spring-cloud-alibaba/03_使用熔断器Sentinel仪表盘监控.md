# 使用熔断器仪表盘监控

## Sentinel 控制台

Sentinel 控制台提供一个轻量级的控制台，它提供机器发现、单机资源实时监控、集群资源汇总，以及规则管理的功能。您只需要对应用进行简单的配置，就可以使用这些功能。

>注意: 集群资源汇总仅支持 500 台以下的应用集群，有大概 1 - 2 秒的延时。

## Sentinel控制台使用

```shell
# 下载源码
git clone https://github.com/alibaba/Sentinel.git

# 编译打包
mvn clean package

# 启动控制台
# Sentinel 控制台是一个标准的 SpringBoot 应用，以 SpringBoot 的方式运行 jar 包即可。

cd sentinel-dashboard\target

java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
# 如若 8080 端口冲突，可使用 -Dserver.port=新端口 进行设置。

# 访问服务（用户和密码默认为sentinel）
打开浏览器访问：http://localhost:8080/#/dashboard/home
```

## 配置控制台信息

>application.yml 配置文件中增加如下配置：

```yml
spring:
  cloud:
    sentinel:
      transport:
        port: 8719 # 应用再对应的机器启动的Http Server，要和Sentinel控制台交互.
        dashboard: localhost:8080 # 就是独立的Sentinel控制台所在的机器ip和port
```

>这里的 spring.cloud.sentinel.transport.port 端口配置会在应用对应的机器上启动一个 Http Server，该 Server 会与 Sentinel 控制台做交互。比如 Sentinel 控制台添加了 1 个限流规则，会把规则数据 push 给这个 Http Server 接收，Http Server 再将规则注册到 Sentinel 中