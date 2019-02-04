### IntelliJ开发Web项目

IntelliJ IDEA是最受欢迎的Java集成开发环境，在实际开
发中被广泛地使用。

本文介绍如何使用IntelliJ IDEA开发传统的Java Web
（即Servlet和JSP）项目的步骤与方法，掌握好它们，就可
以使用IntelliJ来编写程序，深入地学习Java Web开发技
术，同时，这些技能也为实际工作所需要，因此，掌握以下内容是很重要的。

1. File/New Project/Create New
2. 选择“Java Enterprise”,然后勾选 Additional Libraries and Frameworks下的“Web Application”，勾选创建web.xml
3. 注意一下右上角的各项设置(Project SDK, Java EE Version, Application Server(Tomcat或其他Server))，如果为空时，点击“New…”按钮创建相应的配置,然后next，然后给Web项目起名字，然后生成项目模板,IDEA生成的项目模板如下:

```
.
├── src
│   └── cn
│       └── edu
│           └── bit
│               └── cs
│                   └── servlet
│                       ├── FormServlet.java
│                       ├── GenericServletDemoServlet.java
│                       ├── MyFirstServlet.java
│                       ├── ServletConfigDemoServlet.java
│                       └── WelcomeServlet.java
└── web
    ├── index.jsp
    └── WEB-INF
        └── web.xml
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

> 在Run/Debug Configurations窗口中，默认情况下左边列表是空的（除
非你以前创建过相应的配置），点击左上角“+”号，选择“Tomcat Server”，创建一个新的“Local(本地)” Tomcat Server配置。

> 切换到Tomcat配置界面，点击Configure,点击左上角的+,在弹出的对话
窗口中配置Tomcat所在的路径.

> 启动Tomcat并将Web项目部署到它上面运行……

> access: http://localhost:8080/

> 在项目src节点上右击，选择Servlet,向项目中添加一个Servlet……,给Servlet指定类名和包

### 下面再介绍一下如何部署Java Web项目

> File -> Project Structure -> Artifacts -> + -> Web Application: Archive(注意类型不要选错了)-> For 'MyFirstServletProject:war exploded'

> 选中添加好的war Artifacts,在Output Layout下,点击create Manifest,
需要为war创建一个清单文件,在项目的rootDir/web/下创建META-INF文件夹,然后在META-INF文件夹下创建MANIFEST.MF,然后点击ok

> 然后，build -> Build Artifacts -> MyFirstServletProject:war -> Build -> 发现out/artifacts/learnServletProject_war/learnServletProject_war.war已经生成

```
.
├── out
│   └── artifacts
│       └── learnServletProject_war
│           └── learnServletProject_war.war
├── Servlet应用之表单处理.md
├── src
│   └── cn
│       └── edu
│           └── bit
│               └── cs
│                   └── servlet
│                       ├── FormServlet.java
│                       ├── GenericServletDemoServlet.java
│                       ├── MyFirstServlet.java
│                       ├── ServletConfigDemoServlet.java
│                       └── WelcomeServlet.java
└── web
    ├── index.jsp
    ├── META-INF
    │   └── MANIFEST.MF
    └── WEB-INF
```

* IntelliJ中生成的class文件放到classes文件夹下。
* 如果需要第三方jar包，则会在WEB-INF下创建一个lib文件夹，用于保存jar包。
* 在应用程序根目录（与WEB-INF同级）下，还可以创建子文件夹，比如js,css之类，用于保存各种Web资源
* 放在应用程序目录下的任何资源，用户只要输入资源URL，都可以直接访问到。如果
想让某一个资源可以被Servlet访问，但不可以被用户访问，那么就要把它放在WEB-INF目录下。
* 部署描述符总是命名为web.xml，并且放在WEB-INF目录下

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

只要掌握了这些内容，学习JSP和Servlet的大门就己经打开。