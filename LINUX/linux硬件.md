# Linux查看物理CPU个数、核数、逻辑CPU个数

    物理cpu个数：主板上实际插入的cpu数量，可以数不重复的 physical id 有几个（physical id）
    cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l

    每个物理cpu中core的个数(核数)：单块物理CPU上面能处理数据的核的数量，如双核、四核等 （cpu cores）
    cat /proc/cpuinfo| grep "cpu cores"| uniq
    
    每个核逻辑cpu个数,也就是每个核超线程的个数,假设每个核超线程的个数为2
    也就是每个核有2个逻辑处理单元,这2个逻辑处理单元分享一个core的资源
    cat /proc/cpuinfo| grep "processor"| wc -l

    总核数 = 物理CPU个数 X 每颗物理CPU的核数 
    总逻辑CPU数 = 物理CPU个数 X 每个物理的核数 X 超线程数
    
    查看cpu信息（型号）
    cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c

    查看内 存信息:
    cat /proc/meminfo    
    
[解释](https://www.cnblogs.com/bugutian/p/6138880.html)    

# 什么是并发？

    并发指的是在同一时刻执行多个任务.

# 进程与线程

线程称为轻量级进程,线程必须存在于进程中。但是创建一个线程要消耗的资源通常比创建进程少的多。

每个进程至少要有一个线程作为程序的入口(主线程)

# 单核cpu

    一个物理cpu只有一个core
    
    一个物理cpu同一时刻只能处理一条指令.在任一时刻，只有一个进程的中一个线程在运行.

    笔者就曾经遇到过这样的面试题，什么情况下，一个计算机中任一时刻只会有一个线程在运行。
    这个问题很简单，只要你的 "CPU是单核" 的就行了.在单核CPU上，因为两个线程从来不会得到真正的并行执行.

# 多核cpu

    一个物理cpu只有多个core
    
    只有在有多个CPU的情况下，才能实现真正意义上的并行执行
