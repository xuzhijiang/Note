## top命令

>top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况.top是一个动态显示过程,即可以通过用户按键来不断刷新当前状态.如果在前台执行该命令,它将独占前台,直到用户终止该程序为止.top命令提供了实时的对系统处理器的状态监视.该命令可以按CPU使用.内存使用和执行时间对任务进行排序.

```shell
$ top
top - 09:14:56 up 264 days, 20:56,  1 user,  load average: 0.02, 0.04, 0.00
Tasks:  87 total,   1 running,  86 sleeping,   0 stopped,   0 zombie
Cpu(s):  0.0%us,  0.2%sy,  0.0%ni, 99.7%id,  0.0%wa,  0.0%hi,  0.0%si,  0.2%st
Mem:    377672k total,   322332k used,    55340k free,    32592k buffers
Swap:   397308k total,    67192k used,   330116k free,    71900k cached
PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
1 root      20   0  2856  656  388 S  0.0  0.2   0:49.40 init
2 root      20   0     0    0    0 S  0.0  0.0   0:00.00 kthreadd
3 root      20   0     0    0    0 S  0.0  0.0   7:15.20 ksoftirqd/0
4 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 migration/0
```

### 第一行

1. 09:14:56 ： 系统当前时间
2. 264 days, 20:56 ： 系统开机到现在经过了多少时间
3. 1 users ： 当前在线用户
4. load average: 0.02, 0.04, 0.00： 系统1分钟、5分钟、15分钟的CPU负载信息

### 第二行

* Tasks：任务
* 87 total：很好理解，就是当前有87个任务，也就是87个进程。
* 1 running：1个进程正在运行
* 86 sleeping：86个进程睡眠
* 0 stopped：停止的进程数
* 0 zombie：僵死的进程数

### 第三行

* Cpu(s)：表示这一行显示CPU总体信息
* 0.0%us：用户态进程占用CPU时间百分比，不包含renice值为负的任务占用的CPU的时间。
* 0.7%sy：内核占用CPU时间百分比
* 0.0%ni：改变过优先级的进程占用CPU的百分比
* 99.3%id：空闲CPU时间百分比
* 0.0%wa：等待I/O的CPU时间百分比
* 0.0%hi：CPU硬中断时间百分比
* 0.0%si：CPU软中断时间百分比

>注：这里显示数据是所有cpu的平均值，如果想看每一个cpu的处理情况，按1即可；折叠，再次按1

### 第四行

* Men：内存的意思
* 8175320kk total：物理内存总量
* 8058868k used：使用的物理内存量
* 116452k free：空闲的物理内存量
* 283084k buffers：用作内核缓存的物理内存量

### 第五行

* Swap：交换空间
* 6881272k total：交换区总量
* 4010444k used：使用的交换区量
* 2870828k free：空闲的交换区量
* 4336992k cached：缓冲交换区总量

### 进程信息

1. PID：进程的ID
2. USER：进程所有者
3. PR：进程的优先级别，越小越优先被执行
4. NInice：值
5. VIRT：进程占用的虚拟内存
6. RES：进程占用的物理内存
7. SHR：进程使用的共享内存
8. S：进程的状态。S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值为负数
9. %CPU：进程占用CPU的使用率
10. %MEM：进程使用的物理内存和总内存的百分比
11. TIME+：该进程启动后占用的总的CPU时间，即占用CPU使用时间的累加值。
12. COMMAND：进程启动命令名称

### top命令交互操作指令

1. q：退出top命令
2. <Space>：立即刷新
3. s：设置刷新时间间隔
4. c：显示命令完全模式
5. t:：显示或隐藏进程和CPU状态信息
6. m：显示或隐藏内存状态信息
7. l：显示或隐藏uptime信息
8. f：增加或减少进程显示标志(Fields Management,每个字段代表什么信息等，有用)
9. P：按%CPU使用率排序
10. T：按MITE+排序
11. M：按%MEM排序
12. u：指定显示用户进程(有用)
13. kkill：进程
14. i：只显示正在运行的进程

```shell
# 显示指定的进程信息
top -p pid

# 每隔1s打印出cpu的信息
while true;do top;sleep 1;done

# 使用频率最高的是P、T、M.
# 通常使用top，我们就想看看是哪些进程最耗cpu资源、占用的内存最多

# htop 是一个 Linux 下的交互式的进程浏览器，可以用来替换Linux下的top命令
# 与top相比，htop有以下优点
# htop更加人性化。它可让用户交互式操作，支持颜色主题，可横向或纵向滚动浏览进程列表
# 杀进程时不需要输入进程号
# htop 支持鼠标操作
```

### Linux查看物理内存占用率

```shell
# Linux下看内存和CPU使用率一般都用top命令，
# 但是实际在用的时候，用top查看出来的内存占用率都非常高，如：
Mem: 4086496k total, 4034428k used, 52068k free, 112620k buffers
Swap: 4192956k total, 799952k used, 3393004k free, 1831700k cached

# 接近98.7%，而实际上的应用程序占用的内存往往并没这么多，

PID USER PR NI VIRT RES SHR S %CPU %MEM TIME+ COMMAND 
25801 sybase 15 0 2648m 806m 805m S 1.0 20.2 27:56.96 dataserver 
12084 oracle 16 0 1294m 741m 719m S 0.0 18.6 0:13.50 oracle 
27576 xugy 25 0 986m 210m 1040 S 1.0 5.3 28:51.24 cti 
25587 yaoyang 17 0 1206m 162m 3792 S 0.0 4.1 9:21.14 java

# 看%MEM这列的数字，按内存排序后，把前几名加起来，
# 撑死了才不过55%，那剩下的内存都干嘛用了？

# 一般的解释是Linux系统下有一种思想，内存不用白不用，占用了就不释放，
# 听上去有点道理，但如果我一定要知道应用程序还能有多少内存可用呢？

# 仔细看top关于内存的显示输出，有两个数据buffers和cached，
# 在Linux系统下的buffer指的是磁盘写缓存，而cache则指的是磁盘读缓存。

# A buffer is something that has yet to be "written" to disk. 
# 缓冲区是尚未“写入”磁盘的东西。

# A cache is something that has been "read" from the disk and stored for later use.
# 缓存是从磁盘“读取”并存储以供以后使用的东西。


# 而这两块是为了提高系统效率而分配的内存，在内存富余的时候，操作系统将空闲内存利用起来，
# 而有内存需求时，系统会释放这部分的内存供应用程序使用。

这样，真正应用程序可用的内存就是free+buffer+cache，
# 上面的例子就是：52068k + 112620k + 1831700k = 1996388k

# 而已用内存则是used-buffer-cache，上面的例子为：
4034428k - 112620k - 1831700k = 2090108k

# Linux下查看内存还有一个更方便的命令，free:
free

# 查看内存的使用情况
free -h

total used free shared buffers cached
Mem: 4086496 4034044 52452 0 112756 1831564
-/+ buffers/cache: 2089724 1996772(第一列已用内存，第二列是可用内存)
Swap: 4192956 799952 3393004

# Mem:这列就是用top命令看到的内存使用情况，
# 而-/+buffers/cache这列就是我们刚刚做的计算结果：
top上面的的数字: used-buffer-cache=2090108k(已用内存)
free命令的数字: free+buffer+cache=2089724K(可用内存)

# 也可以加-m或者-g参数查看按MB或者GB换算的结果:
free -m

total used free shared buffers cached
Mem: 3990 3906 83 0 90 1786
-/+ buffers/cache: 2029 1961
Swap: 4094 781 3312

# 这样，真正应用程序的内存使用量就可以得出来了，上面的例子中内存占用率为51.1%。

# 参考:https://zhuanlan.zhihu.com/p/25082224
```