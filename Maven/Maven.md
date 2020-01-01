# Maven

>POM 代表项目对象模型.

![](pics/Maven本地仓库路径修改.png)
![](pics/对操作系统所有用户或者单个用户修改maven的本地仓库目录.png)

![](pics/Maven中央仓库01.png)
![](pics/Maven中央仓库02.png)

![](pics/Maven插件01.png)
![](pics/Maven插件02.png)

    idea配置更新快照版本
    
![](pics/maven快照.png)
![](pics/maven-snapshots理解.png)
![](pics/快照版的版本号可以相同,release的版本不能相同.png)
![](pics/Snapshots与Releases的区别.png)

>等到快照版本验证了没啥问题了,再发型release版本.

    idea配置自己的Maven
    
![](pics/更新Maven仓库索引.png)
![](pics/idea配置用户自己的maven.png)

    使用maven刷新项目
    
![](pics/idea-maven刷新.png)

# Maven 常用命令

- 查看maven版本: mvn -version
- mvn package: 打包JAR/WAR/EAR
- mvn package -Dmaven.test.skip=true 只打包不测试（跳过测试）
- mvn compile (just compile)编译源代码
- mvn install -DskipTests 或 mvn install -Dmaven.test.skip=true(跳过测试)
- mvn test(要在不创建应用程序JAR/WAR/EAR文件的情况下仅运行JUnit测试)
- mvn clean(总是建议在编译和构建我们的应用程序代码之前清理所有内容--删除构建目录)
- mvn –help
- mvn install(要编译，构建(构建成jar/war/ear文件)并且安装到本地maven存储库)
- mvn test package
- mvn deploy
- mvn jetty:run(要在Jetty嵌入式服务器上运行---run on Jetty embedded server)
- mvn jetty:run-war(构建WAR文件，并且部署并运行它到嵌入式jetty服务器中)
- mvn jar(从已经编译好的class文件创建JAR文件)
- mvn eclipse:eclipse(生成项目和所有Eclipse所需的文件)
- mvn eclipse:clean(清理Eclipse所需的所有文件)
- mvn help:describe -Dplugin=eclipse(查看eclipse插件的所有命令)
- Goal Prefix:eclipse(目标前缀是eclipse)

# Maven配置文件Scope解释

>scope有compile、test、runtime、provided、system，其中默认的值是compile

1. Compile:缺省值，适用于所有阶段(编译，测试，运行)，会随着项目一起发布，是一个比较强的依赖。打包的时候需要包含进去。
2. Test: 表示依赖项目仅仅参与测试相关的工作，只在测试时使用，用于编译和运行测试代码。不会随项目发布。比较典型的如junit,junit只有在执行单元测试时候需要，当我们进行真正项目发布的时候junit是不需要进行编译和发布的。
3. runtime: 被依赖项目无需参与项目的编译，适用运行和测试阶段。与compile相比，跳过编译而已，举例说明一下：在代码中调用了一个接口一个方法，这个接口并没有对应的实现。这段代码在编译期间并不会报错，但是在代码运行的时候会出现问题。jdbc驱动可以使用runtime的scope，因为只有在真正运行的时候才会调用到驱动的代码。
4. provided: 打包的时候不打包进去,别的设施(例如web容器)会提供。该依赖参与编译，测试，运行等周期。相当于compile，但是在打包阶段做了exclude的动作,例如servlet相关的jar,容器会提供,所以我们项目发布的时候这个包是不需要打到包里的
5. 从参与度(编译，测试，运行)来说，也provided相同，不过被依赖项不会从maven仓库抓，而是从本地文件系统拿，一定需要配合systemPath属性使用。

# Nexus

Nexus 是一个强大的仓库管理器，极大地简化了内部仓库的维护和外部仓库的访问,[download](https://www.sonatype.com/).

# 为什么使用Nexus

如果没有私服，我们所需的jar都需要通过maven中央仓库和第三方的Maven仓库(如阿里云等)下载到本地，而一个团队中的所有人都重复的从maven仓库下载构件无疑加大了仓库的负载和浪费了外网带宽，如果网速慢的话，还会影响项目的进程。很多情况下项目的开发都是在内网进行的，连接不到maven仓库怎么办呢？开发的公共构件怎么让其它项目使用？这个时候我们不得不为自己的团队搭建属于自己的maven私服，这样既节省了网络带宽也会加速项目搭建的进程，当然前提条件就是你的私服中拥有项目所需的所有构件。

- 如果公司开发组的开发环境全部内网，这时如何连接到在互联网上的Maven中央仓库呢？
- 如果公司经常开发一些公共的组件，如何共享给各个开发组，使用拷贝方式吗？如果这样，公共库升级了怎么办？

>搭建私服之后,下载jar包的时候先从私服中获取,如果私服中没有依赖,就先从官服中下载,下载到私服中,再走私服下载到本地仓库,再到你项目里面.

# Windows下使用Nexus

- [http://www.luyixian.cn/news_show_3701.aspx](http://www.luyixian.cn/news_show_3701.aspx)
- [https://cloud.tencent.com/developer/article/1492820](https://cloud.tencent.com/developer/article/1492820)
- [https://www.cnblogs.com/dingyingsi/p/3687077.html](https://www.cnblogs.com/dingyingsi/p/3687077.html)
- [https://www.funtl.com/zh/nexus/](https://www.funtl.com/zh/nexus/#%E6%9C%AC%E8%8A%82%E8%A7%86%E9%A2%91)

![](pics/Nexus01.png)
![](pics/Nexus02.png)

## 分组

![](pics/Nexus03.png)
![](pics/Nexus分组.png)

![](pics/Nexus04.png)
![](pics/Nexus分组02.png)
![](pics/代理仓库.png)
![](pics/宿主仓库.png)
![](pics/仓库组.png)

## 创建各种仓库

![](pics/Nexus05.png)
![](pics/Nexus06.png)
![](pics/Nexus07.png)
![](pics/Nexus08.png)
![](pics/Nexus09.png)
![](pics/Nexus10.png)

## 上传jar到私服

![](pics/往私服上上传代码01.png)
![](pics/往私服上上传代码02.png)

> 上面的配置中我用的是超级管理员的账户，开发项目中可以改为具有部署构件权限的用户就可以了

![](pics/Nexus22.png)
![](pics/snapshot-release-部署jar注意.png)

### 手动部署中央仓库中不存在的第三方jar到私服

```shell script
mvn deploy:deploy-file # deploy-file指的是要部署文件
  -DgroupId=com.google.code.kaptcha 
  -DartifactId=kaptcha 
  -Dversion=2.3
  -Dpackaging=jar # 部署打包方式为jar 
  -Dfile=D:\kaptcha-2.3.jar # jar包的路径 
  -Durl=http://localhost:8081/nexus/content/repositories/releases/
  -DrepositoryId=nexus-releases
```
 
    注意事项：

    建议在上传第三方 JAR 包时，创建单独的第三方 JAR 包管理仓库，便于管理有维护。（maven-3rd),这里为了简便,部署到release仓库即可.
    
    -DrepositoryId=nexus-releases 对应的是 settings.xml 中 Servers 配置的 ID 名称。（需要授权）
    
    windows上不要使用那个蓝cmd执行命令,会一直提示错误,要换成黑色的cmd执行即可.

>除了可以使用命令手动上传,Nexus 3.0 也支持页面上传.

## 从私服下载jar的配置(也就是配置代理仓库)

> 注意,如果修改的是Maven的conf/settings.xml,那么就不用配置单个项目了,因为这个项目所使用的maven都会自动使用settings.xml中的配置.如果修改的是单个项目的pom.xml,则对其他项目无效.

### 给单个项目配置代理仓库

![](pics/单个项目配置私服.png)

>依赖查找顺序: 先从私服中找, 私服中没有,再从本地仓库中找,本地 仓库没有,再从中央仓库找

### 给本机上的所有用户所有项目配置私服

![](pics/所有项目配置私服.png)

>如果我们没有修改配置文件中的mirror的话,是直接从中央仓库下载jar包的:`https://repo.maven.apache.org/maven2`,按照上面的修改后,就是从我们自己的私服下载.

## 其他注意事项

![](pics/下载远程索引.png)
![](pics/设置部署策略01.png)
![](pics/设置部署用户名.png)

# Linux下使用Nexus

- [https://yq.aliyun.com/articles/7427](https://yq.aliyun.com/articles/7427)

# 解决Maven无法自动下载依赖的问题

![](pics/解决Maven无法自动下载依赖的问题01.png)

![](pics/解决Maven无法自动下载依赖的问题02.png)

![](pics/解决Maven无法自动下载依赖的问题03.png)

# Maven镜像仓库替换为阿里云镜像仓库

## 第一种方式: 直接修改Maven配置文件.

```shell
# 修改maven配置文件: $MAVEN_HOME/conf/settings.xml, 或者在$USER_HOME/.m2/下面添加一个settings.xml文件,里面添加:
<!-- 阿里镜像仓库 -->
<mirrors>
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>
        http://maven.aliyun.com/nexus/content/groups/public/
    </url>
    <mirrorOf>central</mirrorOf>
</mirror>
</mirrors>
```

## 第二种: 修改某一个项目,直接引用alimaven

```xml
<repositories>
    <repository>  
        <id>alimaven</id>  
        <name>aliyun maven</name>  
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
        <releases>  
            <enabled>true</enabled>  
        </releases>  
        <snapshots>  
            <enabled>true</enabled>  
        </snapshots>  
    </repository>  
</repositories>  
```

# 如何使用Maven把框架源码安装到本地

    举例: 把org.mybatis:mybatis-spring框架源码安装到本地需要这个插件:

![](pics/使用Maven把框架源码安装到本地.png)    

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-source-plugin</artifactId>
    <version>3.0.1</version>
    <configuration>
      <attach>true</attach>
    </configuration>
    <executions>
      <execution>
        <phase>compile</phase>
        <goals>
          <goal>jar</goal>
        </goals>
      </execution>
    </executions>
</plugin>
```