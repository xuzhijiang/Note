Grunt和Gulp是两个流行的构建和自动化工具，用于JavaScript项目，如NodeJS或jQuery。 Gant是一种主要用于基于Groovy的应用程序的构建工具。 SBT代表Scala Build Tool。 SBT主要用于基于Scala的应用程序。

Gradle是基于Java应用程序的项目构建和自动化工具; 像ivy，Ant和maven之类的东西。 构建工具可帮助我们缩短开发和测试时间，从而提高生产力。

现在，大多数java项目都使用maven或gradle构建工具，因为与ant相比它们具有优势。

与Ant和maven相比，Gradle将提供以下优势。 这就是为什么所-86Z现场，新项目都使用Gradle作为构建工具:

简单一点： Gradle = Ant + Ivy + Maven + Gant

这意味着Gradle结合了所有流行构建工具的最佳功能：

- Ivy和Ant的强大(Power)和灵活性
- Maven易于使用，生命周期管理和依赖管理
- Gant的构建脚本

What is Gradle’s Motto?(什么是Gradle的座右铭？)

“Make the impossible possible, make the possible easy, and make the easy elegant”.

As of now, Gradle works as build and automation tool for the following projects.
截至目前，Gradle作为以下项目的构建和自动化工具:

- Java/Scala/Groovy Based Projects
- Android Projects
- C/C++ Projects
- JavaScript Based Projects

现在我们很清楚，Gradle是我们项目中使用的最佳构建和自动化工具。

Ant的默认构建脚本名称为build.xml，Maven默认构建脚本名称为pom.xml，Gradle默认构建脚本名称为build.gradle

当我们运行“gradle”命令时，它会在当前工作目录中搜索可用的构建脚本名。 如果找到，则执行该构建脚本。 否则，显示一些有用的默认帮助消息。

### Using Gradle Eclipse Plugin

> File >> New >> Other >> Gradle(STS) Project

如果您观察此项目结构，Gradle项目与Maven项目结构相同。 是的，Gradle使用Maven Project结构，但是我们有build.gradle文件而不是pom.xml文件。

#### build our project

1. Right click on Project’s build.gradle file and select “Run As” >> “Gradle build” 
2. We need to type our required Gradle commands “build”,Click on “Apply” button to apply our changes. Then click on “Run” button to start our Gradle build commnad “gradle build”
3. If you observe the console output, it shows “BUILD SUCCESSFUL” message. That means our Gradle build command has executed successfully.

当我们运行gradle build命令时，它会执行以下操作：

- 它编译java文件,生成class文件.
- 它在$ {PROJECT_ROOT_DIR}\build\libs中生成名对应的jar
- 它执行JUnit文件
