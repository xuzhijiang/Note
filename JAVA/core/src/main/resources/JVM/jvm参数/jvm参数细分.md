# 第一类: (-)参数

    java -help
    java -version
    -client: 设置以client模式运行，特点是启动速度比较快，但运行时性能和内存管理效率不高，通常用于PC应用开发和调试
    -server: 设置以server模式运行，特点是启动速度比较慢，但运行时性能和内存管理效率很高，适用于生产环境
    -classpath classpath(简写: -cp classpath): 指定类路径,使用-classpath后jvm将不再使用CLASSPATH中的路径
    -jar: 指定以jar包的形式执行。必须让jar包的manifest文件中声明初始加载的Main-class，当然那Main-class必须有public static void main方法
    -Dproperty=value: 设置系统属性名/值对，可用System.getProperty("property")得到value的值。如果value中有空格，则需要用双引号将该值括起来，该参数通常用于设置系统级全局变量值，如配置文件路径，以便该属性在程序中任何地方都可访问。

# 第二类: (-X)参数

    -Xloggc:file: 将每次GC事件的相关情况记录到一个文件中
    -Xprof: 输出 cpu 配置文件数据

# 第三类: （-XX）参数

    -XX:-DisableExplicitGC	效果就是: 调用System.gc()跟没调一样
    -XX:+MaxFDLimit	最大化文件描述符的数量限制
    -XX:+ScavengeBeforeFullGC	新生代GC优先于Full GC执行
    -XX:+UseGCOverheadLimit	在抛出OOM之前限制jvm耗费在GC上的时间比例
    -XX:-UseConcMarkSweepGC	对老生代采用并发标记交换算法进行GC
    -XX:-UseParallelGC	启用并行GC
    -XX:-UseParallelOldGC	对Full GC启用并行，当-XX:-UseParallelGC启用时该项自动启用
    -XX:-UseSerialGC	启用串行GC
    -XX:+UseThreadPriorities	启用本地线程优先级


    -XX:LargePageSizeInBytes=4m	设置用于Java堆的大页面尺寸
    -XX:MaxHeapFreeRatio=70	GC后java堆中空闲量占的最大比例
    -XX:MinHeapFreeRatio=40	GC后java堆中空闲量占的最小比例
    -XX:ReservedCodeCacheSize=32m	保留代码占用的内存容量
    -XX:+UseLargePages	使用大页面内存


    -XX:-CITime	打印消耗在JIT编译的时间
    -XX:ErrorFile=./hs_err_pid<pid>.log	保存错误日志或者数据到文件中
    -XX:-ExtendedDTraceProbes	开启solaris特有的dtrace探针
    -XX:HeapDumpPath=./java_pid<pid>.hprof	指定导出堆信息时的路径或文件名
    -XX:-HeapDumpOnOutOfMemoryError	当首次遭遇OOM时导出此时堆中相关信息
    -XX:OnError="<cmd args>;<cmd args>"	出现致命ERROR之后运行自定义命令
    -XX:OnOutOfMemoryError="<cmd args>;<cmd args>"	当首次遭遇OOM时执行自定义命令
    -XX:-PrintClassHistogram	遇到Ctrl-Break后打印类实例的柱状信息，与jmap -histo功能相同
    -XX:-PrintConcurrentLocks	遇到Ctrl-Break后打印并发锁的相关信息，与jstack -l功能相同
    -XX:-PrintCommandLineFlags	打印在命令行中出现过的标记
    -XX:-PrintCompilation	当一个方法被编译时打印相关信息
    -XX:-PrintGC	每次GC时打印相关信息
    -XX:-PrintGCDetails	每次GC时打印详细信息
    -XX:-PrintGCTimeStamps	打印每次GC的时间戳
    -XX:-TraceClassLoading	跟踪类的加载信息
    -XX:-TraceClassLoadingPreorder	跟踪被引用到的所有类的加载信息
    -XX:-TraceClassResolution	跟踪常量池
    -XX:-TraceClassUnloading	跟踪类的卸载信息
    -XX:-TraceLoaderConstraints	跟踪类加载器约束的相关信息
    -XX:+PrintHeapAtGC 当发生 GC 时打印内存布局
    -XX:+HeapDumpOnOutOfMemoryError发送内存溢出时 dump 内存
    -XX:PretrnureSizeThreshold=1B 让大对象直接进入老年代: