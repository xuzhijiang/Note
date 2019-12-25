# top命令

    uptime命令是一个top的精简版命令,可以看到load averge

    top能够实时显示cpu和内存占用百分比.

```shell script
top

# 第一个看看cpu和内存Mem
# 第二个看load average: 1.31, 0.65, 0.41,也就是系统的负载均衡,分别代表的是系统1分钟,5分钟,15分钟系统的平均负载值.如果三个值相加除以3再乘100%,如果高于60%,那么就代表系统的负担压力重,说明后台一定有一个高cpu的程序再给你占用.
# 这个命令如果不停的按键盘上的1,可以看到有几个cpu的核,可以看到是每一个cpu它的平均负载
top - 09:14:56 up 264 days, 20:56,  1 user,  load average: 0.02, 0.04, 0.00
Tasks:  87 total,   1 running,  86 sleeping,   0 stopped,   0 zombie
# us代表user,sy代表system
Cpu(s):  0.0%us,  0.2%sy,  0.0%ni, 99.7%id,  0.0%wa,  0.0%hi,  0.0%si,  0.2%st
Mem:    377672k total,   322332k used,    55340k free,    32592k buffers
Swap:   397308k total,    67192k used,   330116k free,    71900k cached
PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
1 root      20   0  2856  656  388 S  0.0  0.2   0:49.40 init
2 root      20   0     0    0    0 S  0.0  0.0   0:00.00 kthreadd
3 root      20   0     0    0    0 S  0.0  0.0   7:15.20 ksoftirqd/0
4 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 migration/0

# 根据上一步top能看出pid
# 通过top -Hp pid可以查看该进程下各个线程的cpu使用情况
top -Hp pid
top -H -p <pid>

# 按理说，都是某个进程下的线程，应该进程id PID一样啊，但实际却都不一样
# 实际是被PID的名字给弄混了，pid除了能代表是process id的意思,还能代表pthread id
# 也就是每个进程中线程的(process thread)id, 在内核中，每个线程都有自己的PID
# linux将线程作为轻量级进程也分配了pid
# 线程ID在某进程中是唯一的，在不同的进程中的线程ID值可能相同
```

    Linux通过进程查看线程的方法:

    1. htop按t(显示进程线程嵌套关系)和H(显示线程) ，然后F4过滤进程名
    2. ps -eLf | grep java (e是显示全部进程，L是显示线程，f全格式输出
    3. top -Hp <pid> (实时) 

## 第一行

1. 09:14:56 ： 系统当前时间
2. 264 days, 20:56 ： 系统开机到现在经过了多少时间
3. 1 users ： 当前在线用户
4. load average: 0.02, 0.04, 0.00： 系统1分钟、5分钟、15分钟的CPU负载信息

## 第二行

* Tasks：任务
* 87 total：很好理解，就是当前有87个任务，也就是87个进程。
* 1 running：1个进程正在运行
* 86 sleeping：86个进程睡眠
* 0 stopped：停止的进程数
* 0 zombie：僵死的进程数

## 第三行

* Cpu(s)：表示这一行显示CPU总体信息
* 0.0%us：用户态进程占用CPU时间百分比，不包含renice值为负的任务占用的CPU的时间。
* 0.7%sy：内核占用CPU时间百分比
* 0.0%ni：改变过优先级的进程占用CPU的百分比
* 99.3%id：空闲CPU时间百分比(id是idle的缩写)
* 0.0%wa：等待I/O的CPU时间百分比
* 0.0%hi：CPU硬中断时间百分比
* 0.0%si：CPU软中断时间百分比

>注：这里显示数据是所有cpu的平均值，如果想看每一个cpu的处理情况，按1即可；折叠，再次按1

## 第四行

* Men：内存的意思
* 8175320kk total：物理内存总量
* 8058868k used：使用的物理内存量
* 116452k free：空闲的物理内存量
* 283084k buffers：用作内核缓存的物理内存量

## 第五行

* Swap：交换空间
* 6881272k total：交换区总量
* 4010444k used：使用的交换区量
* 2870828k free：空闲的交换区量
* 4336992k cached：缓冲交换区总量

## 进程信息

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

# top命令交互操作指令

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

# htop

    htop (需要安装下,这个对人可读性好,而且是彩色显示.yum install htop)