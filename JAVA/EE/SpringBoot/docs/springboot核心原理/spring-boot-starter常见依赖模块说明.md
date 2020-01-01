# spring-boot-starter常见依赖模块说明

- spring-boot-starter-parent: 是一种特殊的starter，包含了一个 dependency-managemen部分，子项目中可以省略 版本号。而其他的starter提供了事实上的依赖。例如要搭建一个 web应用，需要我们提供：spring-boot-starter-web
- spring-boot-starter：核心模块，包括自动配置支持、日志和YAML
- spring-boot-starter-test：测试模块，包括JUnit、Hamcrest、Mockito
