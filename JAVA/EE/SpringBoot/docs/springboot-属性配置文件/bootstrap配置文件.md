- Bootstrap.yml
- bootstrap.properties
- application.yml
- application.properties

bootstrap.yml（bootstrap.properties）在application.yml（application.properties）之前加载,bootstrap.yml优先级更高,
一般bootstrap.yml用在spring cloud中,用于从远程仓库pull配置,把本地的application.yml中的配置覆盖.

## bootstrap典型场景

使用 Spring Cloud Config Server的时候，可以在 bootstrap.yml里面指定spring.application.name和 spring.cloud.config.server.git.uri, 以及一些加密/解密的信息