# tomcat应用部署目录结构

我们的应用程序一般会打包成归档格式（.war），然后放到Tomcat的应用程序部署目录。而webapp有特定的组织格式，通常包含了servlet代码文件、HTML/jsp页面文件、类文件、部署描述符文件等等，相关说明如下：

1. /：web应用程序的根目录，可以存放HTML/JSP页面以及其他客户端浏览器必须可见的其他文件（如js/css/图像文件）。在较大的应用程序中，还可以选择将这些文件划分为子目录层次结构。
2. /WEB-INF：此webapp的所有私有资源目录，用户浏览器不可能访问到的，通常web.xml放置于此目录。
3. /WEB-INF/web.xml：此webapp的私有的部署描述符，描述组成应用程序的servlet和其他组件（如filter），以及相关初始化参数和容器管理的安全性约束。
4. /WEB-INF/classes：此webapp自有的Java程序类文件（.class）及相关资源存放目录。
5. /WEB-INF/lib：此目录存放webapp自有的JAR文件，其中包含应用程序所需的Java类文件（及相关资源），例如第三方类库或JDBC驱动程序。
6. /META-INF: 此webapp的所有私有资源目录，用户浏览器不可能访问到的，通常context.xml放置于此目录。
