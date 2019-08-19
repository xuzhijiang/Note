## ps命令

ps命令是Process Status的缩写.ps命令列出的是当前那些进程的快照.如果想要动态的显示进程信息，就可以使用top命令。

使用该命令可以确定有哪些进程正在运行和运行的状态、进程是否结束、进程有没有僵死、哪些进程占用了过多的资源等等.

>ps工具标识进程的5种状态码:

1. D 不可中断 uninterruptible sleep (usually IO)
2. R 运行 runnable (on run queue)
3. S 中断 sleeping
4. T 停止 traced or stopped
5. Z 僵死 a defunct (”zombie”) process

```shell
# 列出目前所有的正在内存中的程序
ps -aux

# 检查一个redis是否运行
ps -ef | grep redis [-i]

# 显示指定用户信息
ps -u root

# 显示所有进程信息，连同命令行
ps -ef

# ps 与grep 组合使用，查找特定进程
ps -ef | grep ssh

# 将与这次登入相关的 PID(进程id) 与相关信息列示出来
ps -l
```

### 输出列的含义

1. S: 代表这个程序的状态 (STAT)
2. UID: 程序被该 UID 所拥有
3. PID: 进程的ID
4. PPID: 父程序的ID
5. C CPU使用的资源百分比
6. PRI 这个是 Priority (优先执行序) 的缩写
7. SZ 使用掉的内存大小
11. USER：表示启动进程的用户
12. %CPU：表示运行该进程占用CPU的时间与该进程总的运行时间之比。
13. %MEM：表示该进程占用内存与总内存之比。
14. VSZ：表示占用的虚拟内存大小，以KB为单位。
15. RSS：为进程占用的物理内存值，以KB为单位。
16. TTY：表示该进程建立时所对应的终端，“?”表示该进程不占用终端。
17. STAT：表示进程的运行状态。包括以下几种代码：D，不可中断的睡眠；R，就绪（在可运行队列中）；S，睡眠；T，被跟踪或停止；Z，终止（僵死）的进程，这些进程不存在，但暂时无法消除；W，没有足够的内存分页可分配；＜，高优先级的进程；N，低优先级的进程；L，有内存分页分配并锁在内存体内（实时系统或I/O）。
18. START：为进程开始时间。
19. TIME：进程使用掉的 CPU 时间
20. COMMAND：是对应的命令名。

