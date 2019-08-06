我们可以使用Spring Boot开发两种基于Spring的应用程序:

1. 基于Java的应用程序
2. Groovy应用程序

无论如何(Anyhow)，与Java几乎类似,Groovy也是一种JVM语言。我们可以将Groovy和Java结合到一个Project中。因为像Java文件一样，Groovy文件最终只能编译成*.class文件。 * .groovy和*.java文件都转换为*.class文件(相同的字节代码格式）：



Groovy(*.groovy) -----> 
                    Compiler (javac or groovyc) -----> *.class files(Byte Code)
Java(*.java)   ------->
