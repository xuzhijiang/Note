# Linux查看物理内存占用率

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
free -h  (human read)

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

# free -h的available作用:只是一种可用内存的预估计.
free -h
              total        used        free      shared  buff/cache   available
Mem:           992M        420M        104M        412K        467M        401M
Swap:            0B          0B          0B

# The available memory is just a estimate of how memory can be really used in your system for loading programs, so it is not a precise value.

# Estimation of how much memory is available for starting new applications
```

# 参考

- [free](https://unix.stackexchange.com/questions/326833/meaning-of-available-field-in-free-m-command)
- [free](http://man7.org/linux/man-pages/man1/free.1.html)