In Simple Terminology, What Spring Boot means
在简单术语中，Spring Boot意味着什么

Spring Boot只是现有的Spring + 一些嵌入式HTTP服务器(Tomcat/Jetty等）- XML或Annotations配置。

意味着我们不需要编写任何XML配置和少量注释。

#### Spring Boot的主要目标：

1. 完全避免XML配置
2. 避免定义更多注释配置(它将一些现有的Spring注释组合到一个简单的单个注释中）
3. 避免编写大量的import语句
4. 提供一些默认值，以便在短时间内快速启动新项目。
5. 缩短了开发人员的工作量并提高了工作效率

我们可以使用Spring Boot开发两种基于Spring的应用程序:

1. 基于Java的应用程序
2. Groovy应用程序

无论如何(Anyhow)，与Java几乎类似,Groovy也是一种JVM语言。我们可以将Groovy和Java结合到一个Project中。因为像Java文件一样，Groovy文件最终只能编译成*.class文件。 * .groovy和*.java文件都转换为*.class文件(相同的字节代码格式）：

Groovy(*.groovy) -----> 
                    Compiler (javac or groovyc) -----> *.class files(Byte Code)
Java(*.java)   ------->
