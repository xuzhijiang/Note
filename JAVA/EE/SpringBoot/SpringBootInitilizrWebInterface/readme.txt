我们将讨论Spring Boot Initilizr Web Interface及其IDEs或它的IDE插件。

## 什么是Spring Boot Initilizr

除了Spring Boot 4 Components之外，Spring Boot Framework还有另一个重要的关键组件：Spring Boot Initilizr。

Spring团队在“http://start.spring.io/”为Spring Boot Initilizr提供了一个Web界面，可以非常轻松地使用Maven/Gradle构建工具快速启动Spring Boot应用程序的开发。

### 为什么我们需要Spring Boot Initilizr

Spring Boot Initilizr的主要目标是立即开始新的项目开发。它提供了所有必需的项目结构和基本构建脚本文件，可以快速启动Spring Boot应用程序，而不会浪费时间。它减少了开发时间。

Spring Boot Initilizr有以下几种形式：

1. Spring Boot Initilizr With Web Interface
2. Spring Boot Initilizr With IDEs/IDE Plugins
3. Spring Boot Initilizr With Spring Boot CLI
4. Spring Boot Initilizr With Third Party Tools

我们现在将通过一些示例来研究“Spring Boot Initilizr With Web Interface”，并将在我即将发布的帖子中讨论其余三个选项。

### Spring Boot Initilizr Web界面

Spring团队在“http://start.spring.io/”上为Spring Boot Initilizr提供了一个Web界面。我们可以使用它为Maven/ Gradle构建工具创建新的Project基础结构。它不仅生成Project Base结构，还提供所有必需的Spring Boot Jar文件。

我们将开发两个示例,一个是Maven，另一个是Gradle。

### Spring Boot Initilizr Web界面Maven示例

请按照以下步骤为Maven Build工具和Spring STS Suite IDE（或任何IDE，如Eclipse，IntelliJ IDEA等）创建新的Spring Boot WebApplication：

1. 访问“http://start.spring.io/”上的Spring Boot Initilizr。
2. 提供我们的项目所需的详细信息，然后单击“Generate Project”按钮
3. 我已经提供了所有Maven项目所需的详细信息和选定的所需技术复选框

```
Project metadata:

Group: com.journaldev
Artifact: SpringMVCMavenProject
Name: SpringMVCMavenProject
Description: Demo project for Spring Boot
Package Name: SpringMVCMavenProject
Type: Maven Project
Packaging: War
Jave Version: 1.8
Language: Java
Spring Boot Version: 2.1.2

Project dependencies:

Web中勾选: Web, WS
Data中勾选： JPA
```

我选择了H2 Database,并单击“Generate Project”按钮

```
Database中勾选：H2    
```

4. 当我们点击“Generate Project”按钮时，它会创建并且下载一个Maven项目并将其作为“SpringMVCMavenProject.zip”文件下载到我们的本地文件系统中。
5. 将“SpringMVCMavenProject.zip”复制到我们的Spring STS Suite workspace，并解压缩此zip文件
6. 将此“SpringMVCMavenProject”Maven项目导入Spring STS IDE

### Spring Boot Initilizr Web Interface Gradle Example

与Maven示例一样，请按照以下步骤为Gradle Build工具和Spring STS Suite IDE创建新的Spring Boot WebApplication。

1. 请访问“http://start.spring.io/”上的Spring Boot Initilizr。
2. 提供我们的项目所需的详细信息，然后单击“生成项目”按钮
3. 我已经提供了所有Gradle项目所需的详细信息和选定的所需技术复选框，如图所示。

```
Project metadata:

Group: com.journaldev
Artifact: SpringMVCGradleProject
Name: SpringMVCGradleProject
Description: Demo project for Spring Boot
Package Name: SpringMVCGradleProject
Type: Gradle Project
Packaging: War
Jave Version: 1.8
Language: Java
Spring Boot Version: 2.1.2

Project dependencies:

Web中勾选: Web, WS
Data中勾选： JPA
```

我选择了H2 Database,并单击“Generate Project”按钮

```
Database中勾选：H2    
```

注意： - 这里与以前的Maven项目的区别仅在于将“类型”从“Maven项目”更改为“Gradle项目”。像这样我们可以选择Java版本，选择语言（Java，Groovy），必需的项目技术等，并且非常容易地创建新项目。

* 当我们单击“生成项目”按钮时，它会创建Gradle项目并将其作为“SpringMVCGradleProject.zip”文件下载到我们的本地文件系统中。
* 将“SpringMVCGradleProject.zip”复制到我们的Spring STS Suite工作区并解压缩此zip文件
* 将此“SpringMVCGradleProject”Gradle项目导入Spring STS IDE
* 这个“SpringMVCGradleProject”Gradle项目结构如图所示Maven项目，除了pom.xml文件更改为build.gradle

这是关于使用Web界面的Spring Boot Initilizr。

我们将讨论Java源代码，“SpringApplication”类的重要性及其“SpringApplication.run（）”方法，如何运行此应用程序等在我后面的帖子“Spring Boot Initilizr With IDEs or IDE Plugins”, “Spring Boot Initilizr With Spring Boot CLI” and “Spring Boot Initilizr With ThirdParty Tools”。