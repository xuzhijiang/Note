### 使用Maven构建Web应用

#### Jave web war包目录结构说明

1. 如果需要第三方jar包，则会在WEB-INF(WEB INFormation)下创建一个lib文件夹，用于保存jar包。
2. 在应用程序根目录(与WEB-INF同级）下，还可以创建子文件夹，比如js,css之类，用于保存各种Web资源
3. 放在应用程序目录下的任何资源，用户只要输入资源URL，都可以直接访问到。如果
想让某一个资源可以被Servlet访问，但不可以被用户访问，那么就要把它放在WEB-INF目录下。
4. 部署描述符总是命名为web.xml，并且放在WEB-INF目录下

> java的web应用，其标准打包方式是war， war和jar类似，只不过它包含更多内容，如jsp文件、servlet文件、java类、web.xml配置，依 赖包、静态web资源(html css js文件)等。

war文件目录结构如下：

```
├── war
│   ├── css
│   ├── img
│   ├── index.html
│   ├── js
│   ├── META-INF
│   ├── sample.jsp
│   └── WEB-INF
│       ├── classes
│       │   ├── config.properties
│       │   ├── FirstServlet.class
│       │   ├── ServeltTest.class
│       │   └── ServletA.class
│       ├── lib
│       │   └── dom.jar
│       └── web.xml
```

jar文件目录结构如下：

```
-jar/
  +META-INF/
  +packagename
  |+ java.class
  |+ ...
```

> 一个WAR包至少包含两个子目录：META-INF和WEB-INF。

1. META-INF：包含一些打包元数据，打包时会涉及到。
2. WEB-INF：必须包含web资源描述文件web.xml。classes包含所有该web项目的类，而lib则包含所有web项目依赖的JAR包，classes和lib目录都会在运行的时候被加入到Classpath中。
3. war包中的还包含了其他web资源，如html或者jsp，js，css等。

#### Maven对web项目的布局约定

1. maven对于web项目有一个通用的约定，不过用户必须显示制定web项目的打包类型为war才会按照正确的方式打包web项目,也就是才会maven才会按照次约定打包
2. web项目的类及资源文件同一般的JAR项目一样，默认位置:`src/main/java和src/main/resources`。测试及测试资源文件默认位置:`src/test/java`和`src/test/resources`。Web项目比较特殊的地方在于：他有一个web资源目录，默认位置:`src/main/webapp/`,(注意文件夹的名字必须叫webapp，否则maven不会将里面的资源打包到war中)
3. 在src/main/webapp目录下除了html、jsp、css等必须包含子目录WEB-INF,且子目录必须包含web.xml文件。
4. 在使用Maven创建Web项目之前必须理解Maven结构和WAR包结构的对应关系.
5. 注意：WAR包中有一个lib目录包含所有依赖JAR包，但Maven项目结构中没有这样一个目录，这是因为依赖都配置在POM中，M aven在使用WAR方式打包的时候会根据POM的配置从本地仓库复制相应的JAR文件。
6. 而对于JAR方式打包的时候不会把依赖打包进去，但是会在META-INF文件中包含pom.xml文件信息用于表明该jar包的依赖情况。

### Eclipse的Jave web项目结构

```
.
├── build
│   └── classes
├── src
└── WebContent
    ├── login.html
    ├── LoginSuccess.jsp
    ├── META-INF
    │   └── MANIFEST.MF
    └── WEB-INF
        └── web.xml
```

### IntelliJ开发Web项目

本文介绍如何使用IntelliJ IDEA开发传统的Java Web
(即Servlet和JSP）项目的步骤与方法，掌握好它们，就可
以使用IntelliJ来编写程序，深入地学习Java Web开发技
术，同时，这些技能也为实际工作所需要，因此，掌握以下内容是很重要的。

1. File/New Project/Create New
2. 选择“Java Enterprise”,然后勾选 Additional Libraries and Frameworks下的“Web Application”，勾选创建web.xml
3. 注意一下右上角的各项设置(Project SDK, Java EE Version, Application Server(Tomcat或其他Server))，如果为空时，点击“New…”按钮创建相应的配置,然后next，然后给Web项目起名字，然后生成项目模板,IDEA生成的项目模板如下:

```
注意区分idea的web项目结构和maven的web项目结构,以下是idea的项目结构.
.
├── .classpath
├── .project
├── out
│   └── artifacts
│       └── learnServletProject_war
│           └── learnServletProject_war.war
├── src
└── web
    ├── index.jsp
    ├── META-INF
    │   └── MANIFEST.MF
    └── WEB-INF
```

#### 创建一个全局的库以便以后使用

> File -> Project Structure -> Global Libraries -> + -> Java
-> 选择Tomcat安装文件夹下的lib子文件夹，它包
容Java Web项目所需的各种jar包 -> ok -> 给lib取名tomcat-8.5.

> 然后在File -> Project Structure -> Modules -> Dependencies -> + -> 使项目依赖于tomcat-8.5的库，并且其Scope是Provided,表明
这些包是由外部容器提供的.

> 如果IntelliJ检测到了不一致，点击Project Structure -> Artifacts的fix按钮将lib的scope
修正为“provided(change lib scope to provided)”

> 在完成了编译相关的设置之后，我们接着需要设置“Run”相关的配置:

> Run -> Edit Configurations...

> 在Run/Debug Configurations窗口中，默认情况下左边列表是空的(除
非你以前创建过相应的配置），点击左上角“+”号，选择“Tomcat Server”，创建一个新的“Local(本地)” Tomcat Server配置。

> 切换到Tomcat配置界面，点击Configure,点击左上角的+,在弹出的对话
窗口中配置Tomcat所在的路径.

> 启动Tomcat并将Web项目部署到它上面运行……

> access: http://localhost:8080/

> 在项目src节点上右击，选择Servlet,向项目中添加一个Servlet……,给Servlet指定类名和包

### 如何打包WAR并且部署WAR到Web容器

1. File -> Project Structure -> Artifacts -> + -> Web Application: Archive(注意类型不要选错了)-> For 'MyFirstServletProject:war exploded'
2. 选中添加好的war Artifacts,点击create Manifest,需要为war创建一个清单文件,在项目的rootDir/web下创建META-INF文件夹,然后在META-INF文件夹下创建MANIFEST.MF,然后点击ok
3. 然后，build -> Build Artifacts -> MyFirstServletProject:war -> Build -> 发现out/artifacts/learnServletProject_war/learnServletProject_war.war已经生成(如果是maven项目，会在target目录下生成war)

> 将war包复制到Tomcat的webapps文件夹下(把war改个好一点的名字……)，例如我的war包名字为XzjFirstServlet.war，Tomcat启动之后，可以发现war包会被自解压为XzjFirstServlet文件夹，然后访问
http://localhost:8080/XzjFirstServlet/,此时页面会显示XzjFirstServlet文件夹下的index.jsp

http://localhost:8080/myfirstservlet/myfirst,此时页面会显示Hello from servlet,表明MyFirstServlet运行正常。

## 小结

使用IntelliJ开发Java Web应用，其配置主要分为两块：

1. 配置项目引用Tomcat的lib文件夹，获取其中的jar包以便
顺利编译JSP和Servlet，这是“编译型配置”。
2. 指定“运行时配置”，通常是设置调试项目时启动Tomcat本
地Server。

> 项目开发完毕后之后，通常是将所有文件打包为“war”，然后部
署到Tomcat的webapps文件夹下。
