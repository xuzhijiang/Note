## volatile关键字是如何保证可见性的

在单CPU的情况下，是不存在可见性问题的，如果是多CPU，可见性问题就会暴露出来。

线程中运行的代码最终都是交给CPU执行，而代码执行时所需使用到的数据来自于内存(或者称之为主存)。但是CPU是不会直接操作内存的，每个CPU都会有自己的缓存，操作缓存的速度比操作主存更快。

因此当某个线程需要修改一个数据时，事实上步骤是如下的：

1. 将主存中的数据加载到缓存中
2. CPU对缓存中的数据进行修改
3. 将修改后的缓存中的值刷新到内存中

多个线程操作同一个变量的情况，则可以用下图表示:

![可见性](./可见性.png)

1. 线程1、线程2、线程3操作的是主存中的同一个变量，并且分别交由CPU1、CPU2、CPU3处理。
2. 3个CPU分别将主存中变量加载到缓存中
3. 各自将修改后的缓存中的值刷新到主存中

>问题就出现在第二步，因为每个CPU操作的是各自的缓存，所以不同的CPU之间是无法感知其他CPU对这个变量的修改的，最终就可能导致结果与我们的预期不符。

而使用了volatile关键字之后，情况就有所不同，volatile关键字有两层语义：

1. 立即将缓存中数据写会到内存中
2. 其他处理器通过嗅探总线上传播过来了数据监测自己缓存的值是不是过期了，如果过期了，就会对应的缓存中的数据置为无效。而当处理器对这个数据进行修改时，会重新从内存中把数据读取到缓存中进行处理。

简而言之就是: 当一个变量被 `volatile` 修饰时，任何线程对它的写操作都会立即刷新到主内存中，并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据。

>在这种情况下，不同的CPU之间就可以感知其他CPU对变量的修改，并重新从内存中加载更新后的值，因此可以解决可见性问题。注意: `volatile` 修饰之后并不是让线程直接从主内存中获取数据，依然需要将变量拷贝到告诉缓存中

## 指令重排

内存可见性只是 `volatile` 的其中一个语义，它还可以防止 `JVM` 进行指令重排优化。

举一个伪代码:

```java
int a=10 ;//1
int b=20 ;//2
int c= a+b ;//3
```

一段特别简单的代码，理想情况下它的执行顺序是：`1>2>3`。但有可能经过 JVM 优化之后的执行顺序变为了 `2>1>3`。

可以发现不管 JVM 怎么优化，前提都是保证`单线程`(这里是重点，是保证单线程最终结果不变，而不能保证多线程最终结果不变)中最终结果不变的情况下进行的。可能这里还看不出有什么问题，那看下一段伪代码:

```java
private static Map<String,String> value ;
private static volatile boolean flag = fasle ;

//以下方法发生在线程 A 中 初始化 Map
public void initMap(){
	//耗时操作
	value = getMapValue() ;//1
	flag = true ;//2
}


//发生在线程 B中 等到 Map 初始化成功进行其他操作
public void doSomeThing(){
	while(!flag){
		sleep() ;
	}
	//dosomething
	doSomeThing(value);
}

```

这里就能看出问题了，当 `flag` 没有被 `volatile` 修饰时，`JVM` 对 1 和 2 进行重排，导致 `value` 都还没有被初始化就有可能被线程 B 使用了。`所以加上 volatile之后可以防止这样的重排优化，保证业务的正确性。`

## 指令重排的的应用

一个经典的使用场景就是`双重懒加载的单例模式`了:

```java
public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            // 多个线程有可能都等在这里，这个时候singleton有可能
            // 还是null，所以加上了volatile关键字，让每个线程及时知道singleton是否应被初始化了，
            // 所以内部又加了一层判断是否为null的if判断
            synchronized (Singleton.class) { // 这里再一次证明了volatile只能保证可见性，不能保证原子性.
                if (singleton == null) {
                    // 防止指令重排
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

这里的 `volatile` 关键字主要是为了防止指令重排。 

如果不用 ，`singleton = new Singleton();`，这段代码其实是分为三步：
- 分配内存空间。(1)
- 初始化对象。(2)
- 将 `singleton` 对象指向分配的内存地址。(3)

加上 `volatile` 是为了让以上的三步操作顺序执行，反之有可能第二步在第三步之前被执行就有可能某个线程拿到的单例对象是还没有初始化的，以致于使用的时候报错。

## 总结

`volatile` 在 `Java` 并发中用的很多，比如像 `Atomic` 包中的 `value`、以及 `AbstractQueuedLongSynchronizer` 中的 `state` 都是被定义为 `volatile` 来用于保证内存可见性。

将这块理解透彻对我们编写并发程序时可以提供很大帮助。