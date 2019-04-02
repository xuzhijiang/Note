## Channel

### Channel的理解以及Channel与流的不同

通道(Channel)可以理解为数据传输的管道。通道与流不同的是，流只是在一个方向上移动
(一个流必须是inputStream或者outputStream的子类)，而通道可以用于读、写或者同时用于读写。

public interface Channel{}顶级类位于java.nio.channels包下，channel本身不能直接访问数据，
channel只能与buffer交互。channel本身不存储数据，因此需要配合缓冲区进行传输。

>channel类的继承关系见图:`Channel类的集成关系.png`,(为了清晰的显示我们的关注点，只显示了我们关心的Channel：
FileChannel，SocketChannel，ServerSocketChannel，DatagramChannel)

I/O 可以分为广义的两大类别： `File I/O`, `Stream I/O`,那么相应地有两种类型的通道也就不足为怪了，它们是文件（FileChannel 类）通道和套接字（ socket---SocketChannel、 ServerSocketChannel 和 DatagramChannel）通道.

### 通道的单向与双向以及通道的读和写

```java
// 通道可以是单向（ unidirectional）或者双向的（ bidirectional）。一个 channel 类可能实现了 ReadableByteChannel 接口以提供read()方法，
// 而另一个 channel 类也许实现 WritableByteChannel 接口以提供 write()方法
// 实现这两种接口其中之一的类都是单向的，只能在一个方向上传输数据。
// 如果一个类同时实现这两个接口，那么它是双向的，可以双向传输数据。

// 可以看到read和write方法接受的都是一个ByteBuffer参数，其中read方法，就是往ByteBuffer中put数据，write方法就是将ByteBuffer中的数据get出来，以便发送给其他远程主机。

// 两种方法均返回已传输的字节数，可能比缓冲区的字节数少甚至可能为零。缓冲区的position位置也会发生与已传输字节相同数量的前移。
// 如果只进行了部分传输，缓冲区可以被重新提交给通道并从上次中断的地方继续传输。
// 该过程重复进行直到缓冲区的 hasRemaining( )方法返回 false 值。
public interface ReadableByteChannel extends Channel {
    public int read(ByteBuffer dst) throws IOException;
}

public interface WritableByteChannel extends Channel{
    public int write(ByteBuffer src) throws IOException;
}
```

>在上面的类图中，我们可以看到FileChannel、SocketChannel通道都实现了这两个接口。
从类定义的角度而言，这意味着FileChannel、SocketChannel 通道对象都是双向的。
这对于 SocketChannel 不是问题，因为它们一直都是双向的，不过对于FileChannel 却是个问题了。

我们知道，一个文件可以在不同的时候以不同的权限打开。从 FileInputStream 对象的getChannel( )方法获取的 FileChannel 对象是只读的，
不过从接口声明的角度来看却是双向的，因为FileChannel 实现 ByteChannel 接口。在这样一个通道上调用 write( )方法将
抛出未经检查的NonWritableChannelException 异常，因为 FileInputStream 对象总是以 read-only 的权限打开文件。

### 如何创建通道

```java
// 通道可以以多种方式创建。Socket 通道有可以直接创建新 socket 通道的工厂方法。

// 但是一个FileChannel 对象却只能通过在一个打开的 RandomAccessFile、 FileInputStream 或 FileOutputStream对象上调用 
// getChannel( )方法来获取。您不能直接创建一个 FileChannel 对象。

public void createChannelExample() {
    SocketChannel sc = SocketChannel.open( );
    sc.connect(new InetSocketAddress("somehost", someport));

    ServerSocketChannel ssc = ServerSocketChannel.open( );
    ssc.socket( ).bind (new InetSocketAddress (somelocalport));
     
    DatagramChannel dc = DatagramChannel.open( );
     
    RandomAccessFile raf = new RandomAccessFile ("somefile", "r");
    FileChannel fc = raf.getChannel( );
}
```

java.net 的 socket 类也有新的 getChannel( )方法。这些方法虽然能返回一个相应的 socket 通道对象，但它们却并非新通道的来源，RandomAccessFile.getChannel( )方法才是。只有在已经有通道存在的时候，它们才返回与一个 socket 关联的通道；它们永远不会创建新通道。

## Socket通道详解

### ServerSocketChannel和SocketChannel的异同

在通道类中，DatagramChannel 和 SocketChannel 实现定义读和写功能的接口而 ServerSocketChannel不实现。ServerSocketChannel 负责监听传入的连接和创建新的 SocketChannel 对象，它本身从不传输数据。

### Socket和Channel的关系

全部 NIO中的socket通道类（DatagramChannel、 SocketChannel 和 ServerSocketChannel）在被实例化时都会创建一个对等的BIO中的 socket 对象（ Socket、 ServerSocket和 DatagramSocket）。DatagramChannel、 SocketChannel 和 ServerSocketChannel通道类都定义了socket()方法，我们可以通过这个方法获取其关
联的socket对象。另外每个Socket、 ServerSocket和 DatagramSocket都定义了getChannel()方法，来获取对应的通道。

>需要注意是，只有通过通道类创建的socket对象，其getChannel方法才能返回对应的通道，如果直接new了socket对象，那么其getChannel方法返回的永远是null。

### 非阻塞模式

>通道可以以阻塞（ blocking）或非阻塞（ nonblocking）模式运行。非阻塞模式的通道永远不会让调用的线程休眠。
请求的操作要么立即完成，要么返回一个结果表明未进行任何操作。

这个陈述虽然简单却有着深远的含义。传统Java socket的阻塞性质曾经是Java 程序可伸缩性的最重要制约之一。非阻塞 I/O 是许多复杂的、高性能的程序构建的基础。回顾我们之前讲解的BIO编程中，不能"以尽可能少的线程，
处理尽可能多的client请求"，就是因为通过Socket的getInputStream方法的read方法是阻塞的，一旦没有数据可读，处理线程就会被一直被block住。

```java
//默认情况下，一个通道创建，总是阻塞的，我们可以通过调用configureBlocking(boolean)方法即可，传递参数值为 true 则设为阻塞模式，参数值为 false 值设为非阻塞模式。而 isBlocking()方法来判断某个 socket 通道当前处于哪种模式
SocketChannel sc = SocketChannel.open( );
sc.configureBlocking (false); // nonblocking
    ...
   if ( ! sc.isBlocking( )) {
       doSomething (cs);
}
```

偶尔地，我们也会需要防止 socket 通道的阻塞模式被更改。 API 中有一个 blockingLock( )方法，该方法会返回一个非透明的对象引用。返回的对象是通道修改阻塞模式时内部使用的对象。只有拥有此对象的锁的线程才能更改通道的阻塞模式，对于确保在执行代码的关键部分时 socket 通道的阻塞模式不会改变以及在不影响其他线程的前提下暂时改变阻塞模式来说，
这个方法都是非常方便的。
 
 ```java
 Socket socket = null;
 Object lockObj = serverChannel.blockingLock( );
 // 执行关键代码部分的时候，使用这个锁进行同步
 synchronize (lockObj)
 {
 // 一旦进入这个部分，锁就被获取到了，其他线程不能改变这个channel的阻塞模式
 boolean prevState = serverChannel.isBlocking( );
 serverChannel.configureBlocking (false);
 socket = serverChannel.accept( );
 serverChannel.configureBlocking (prevState);
 }
 // 释放锁，此时其他线程可以修改channel的阻塞模式
 if (socket != null) {
 doSomethingWithTheSocket (socket);
 }
 ```
 
### ServerSocketChannel
 
我们从最简单的ServerSocketChannel来开始对 socket 通道类的讨论。

ServerSocketChannel 是一个基于通道的socket 监听器。它同我们所熟悉的java.net.ServerSocket执行相同的基本任务，
不过它增加了通道语义，因此能够在非阻塞模式下运行。

用静态的 open( )工厂方法创建一个新的 ServerSocketChannel 对象，将会返回同一个未绑定的java.net.ServerSocket 关联的通道。
该对等 ServerSocket 可以通过在返回的 ServerSocketChannel 上调用 socket( )方法来获取。作为 
ServerSocketChannel 的对等体`被创建的 ServerSocket 对象依赖通道实现`。这些 socket 关联的 SocketImpl 能识别通道。通道不能被封装在随意的 socket 对象外面。
 
```java
// 由于 ServerSocketChannel 没有 bind( )方法，因此有必要取出对等的 socket 并使用它来绑定到一个端口以开始监听连接。我们也是使用对等 ServerSocket 的 API 来根据需要设置其他的 socket 选项。
ServerSocketChannel ssc = ServerSocketChannel.open( );
ServerSocket serverSocket = ssc.socket();
// Listen on port 1234
serverSocket.bind (new InetSocketAddress (1234));
```
 
同它的对等体 java.net.ServerSocket 一样， ServerSocketChannel 也有 accept( )方法。一旦您创建了一个 ServerSocketChannel 
并用对等 socket 绑定了它，然后您就可以在其中一个上调用 accept( )。如果您选择在 ServerSocket 上调用 accept( )方法，那么它会同任何其他的 ServerSocket 表现一样的行为：总是阻塞并返回一个 java.net.Socket 对象。如果您选择在 ServerSocketChannel 上调用 accept( )方法则会返回 SocketChannel 类型的对象，返回的对象能够在非阻塞模式下运行。
 
`如果以非阻塞模式被调用，当没有传入连接在等待时， ServerSocketChannel.accept( )会立即返回null。正是这种检查连接而不阻塞的能力实现了可伸缩性并降低了复杂性。可选择性也因此得到实现。`我们可以使用一个选择器实例来注册一个ServerSocketChannel对象以实现新连接到达时自动通知的功能。代码演示了如何使用一个非阻塞的 accept()方法：org.netty.core.channel.ChannelAccept.java
 
### SocketChannel
 
>开始学习 SocketChannel，它是使用最多的 socket 通道类：

#### SocketChannel和Socket的关系

Socket 和 SocketChannel 类封装点对点、有序的网络连接，就是我们所熟知并喜爱的 TCP/IP网络连接。 SocketChannel扮演客户端发起的连接。直到连接成功，它才能收到数据并且只会从连接到的地址接收。
 
>每个SocketChannel对象创建时都是同一个对等的java.net.Socket对象串联的。静态的 open( )方法可以创建一个新的 SocketChannel 对象，而在新创建的 SocketChannel 上调用 socket( )方法能返回它对等的 Socket 对象；在该 Socket 上调用 getChannel( )方法则能返回最初的那个 SocketChannel。

新创建的SocketChannel 虽已打开却是未连接的。在一个未连接的 SocketChannel对象上尝试一个 I/O 操作会导致 NotYetConnectedException 异常。我们可以通过在通道上直接调用 connect( )方法或在通道关联的 Socket 对象上调用connect( )来将该 socket 通道连接。一旦一个 socket 通道被连接，它将保持连接状态直到被关闭。您可以通过调用
布尔型的 isConnected()方法来测试某个SocketChannel 当前是否已连接。下面两段代码是等价的:
 
```java
// 静态的 open( )方法可以创建一个新的 SocketChannel 对象，而在新创建的 SocketChannel 上调用 socket( )方法能返回它对等的 Socket 对象
SocketChannel socketChannel = SocketChannel.open (new InetSocketAddress ("somehost", somePort));

// 通过connect方法
SocketChannel socketChannel = SocketChannel.open( );
//我们可以通过在通道上直接调用 connect()方法将该 socket 通道连接.它将保持连接状态直到被关闭.
socketChannel.connect (new InetSocketAddress ("somehost", somePort));
```

如果您选择使用传统方式进行连接——通过在对等 Socket 对象上调用 connect( )方法，那么传统的连接语义将适用于此。线程在连接建立好或超时过期之前都将保持阻塞。如果您选择通过在通道上直接调用 connect( )方法来建立连接并且通道处于阻塞模式（默认模式），那么连接过程实际上是一样的。

#### SocketChannel的非阻塞模式

在 SocketChannel 上并没有一种 connect()方法可以让您指定超时（ timeout）值，当 connect( )方法在非阻塞模式下被调用时 SocketChannel 提供并发连接：它发起对请求地址的连接并且立即返回值。如果返回值是true，说明连接立即建立了（这可能是本地环回连接）；
如果连接不能立即建立， connect( )方法会返回 false 且并发地继续连接建立过程。
 
`面向流的的 socket建立连接状态需要一定的时间，因为两个待连接系统之间必须进行包对话以建立维护流 socket 所需的状态信息。`
跨越开放互联网连接到远程系统会特别耗时。假如某个SocketChannel上当前正有一个并发连接， 
isConnectPending()方法就会返回true值。

调用 finishConnect()方法来完成连接过程，该方法任何时候都可以安全地进行调用。
假如在一个非阻塞模式的 SocketChannel 对象上调用 finishConnect( )方法，将可能出现下列情形之一：
 
1. connect( )方法尚未被调用。那么将产生 NoConnectionPendingException 异常。
2. 连接建立过程正在进行，尚未完成。那么什么都不会发生， finishConnect( )方法会立即返回false 值。
3. 在非阻塞模式下调用 connect( )方法之后， SocketChannel 又被切换回了阻塞模式。那么如果有必要的话，
调用线程会阻塞直到连接建立完成， finishConnect( )方法接着就会返回 true值。
4. 在初次调用 connect( )或最后一次调用 finishConnect( )之后，连接建立过程已经完成。那么SocketChannel 
对象的内部状态将被更新到已连接状态， finishConnect( )方法会返回 true值，然后 SocketChannel 对象就可以被用来传输数据了。
5. 连接已经建立。那么什么都不会发生， finishConnect( )方法会返回 true 值。

```java
// 当通道处于中间的连接等待（connection-pending）状态时，您只可以调用 finishConnect( )、isConnectPending()或 isConnected( )方法。一旦连接建立过程成功完成， isConnected( )将返回 true值。
InetSocketAddress addr = new InetSocketAddress (host, port);
SocketChannel sc = SocketChannel.open( );
sc.configureBlocking (false);
sc.connect(addr);
while (!sc.finishConnect()) {
   doSomethingElse( );
}
doSomethingWithChannel (sc);
sc.close( );
```