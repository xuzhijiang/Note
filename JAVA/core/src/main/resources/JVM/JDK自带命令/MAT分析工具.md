# MAT分析统计(Memory Analyzer Tool)

MAT是一个eclipse的插件。它能够快速的分析dump文件，可以直观的看到各个对象在内存占用的量大小，以及类实例的数量，对象之间的引用关系，找出对象的GC Roots相关的信息，此外还能生成内存泄露报表，疑似泄露大对象的报表等等。

# 安装MAT

    可以选择单独MAT程序下载安装http://www.eclipse.org/mat/downloads.php (推荐方式)
    
    也可以选择eclipse插件的方式安装http://download.eclipse.org/mat/1.8.1/update-site/

# 内存溢出时，自动保存dump文件

    前面是手工导出内存dump映射文件，如果应用已经在线上运行，为了能获取应用出现内存溢出时获得heap dump文件，
    以便在之后分析，可以在JVM启动时指定参数：-XX:+HeapDumpOnOutOfMemoryError，
    JVM会在遇到OutOfMemoryError时保存一个“堆转储快照”，并将其保存在一个文件中。 
    文件路径通过-XX:HeapDumpPath指定。

    设置虚拟机参数为：-Xmx40m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\Java\dump.hprof

    生成了文件之后，就可以通过MAT打开来进行分析