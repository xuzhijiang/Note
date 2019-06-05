java.io.File 类包含了4种静态分隔符变量，

1. File.separator: Platform dependent default name-separator character as String. 
For windows, it’s ‘\’ and for unix it’s ‘/’.

2. File.separatorChar: Same as separator but it’s char.

3. File.pathSeparator: Platform dependent variable for path-separator. 
For example PATH or CLASSPATH variable list of paths separated 
by ‘:’ in Unix systems and ‘;’ in Windows system.
例如，在Unix系统中由'：'分隔的路径的PATH或CLASSPATH变量列表和Windows系统中的';'。

4. File.pathSeparatorChar: Same as pathSeparator but it’s char.
与pathSeparator相同，但它是char。