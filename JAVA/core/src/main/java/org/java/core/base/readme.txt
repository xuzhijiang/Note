The class path is the path that the Java Runtime Environment (JRE) 
searches for classes and other resource files.

The class search path (more commonly known by the shorter name, "class path") 
can be set using either the -classpath option when calling a JDK tool 
(the preferred method) or by setting the CLASSPATH environment variable. 
The -classpath option is preferred because you can set it individually 
for each application without affecting other applications and without other 
applications modifying its value.

C:> sdkTool -classpath classpath1;classpath2...

example:

	C:> java -classpath C:\java\MyClasses\myclasses.jar

-or-

C:> set CLASSPATH=classpath1;classpath2...

where:

sdkTool
A command-line tool, such as java, javac, javadoc, or apt. For a listing, see JDK Tools.

For example, suppose you want the Java runtime to find a class named Cool.class 
in the package utility.myapp. If the path to that directory is C:\java\MyClasses\utility\myapp, 
you would set the class path so that it contains C:\java\MyClasses.

To run that app, you could use the following JVM command:

C:> java -classpath C:\java\MyClasses utility.myapp.Cool

But when classes are stored in an archive file (a .zip or .jar file) the class 
path entry is the path to and including the .zip or .jar file. For example, 
to use a class library that is in a .jar file, the command would look something like this:

C:> java -classpath C:\java\MyClasses\myclasses.jar utility.myapp.Cool
    
Multiple specifications

To find class files in the directory C:\java\MyClasses as well as classes in 
C:\java\OtherClasses, you would set the class path to:

C:> java -classpath C:\java\MyClasses;C:\java\OtherClasses ...