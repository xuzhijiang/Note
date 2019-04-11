package org.netty.core.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

public class SelectorBase {

    public static void main(String[] args) throws IOException {
        // 创建选择器
        createSelector();

        register();
    }

    /**
     * 注册通道到选择器上
     */
    private static void register() throws IOException {
        // 注册通道的案例代码
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("localhost", 80));
        // 通道(Channel)在被注册到一个Selector上之前，必须先设置为非阻塞模式（通过调用 configureBlocking(false)）。
        // 如果您试图注册一个处于阻塞状态的通道， register( )将抛出未检查的 IllegalBlockingModeException 异常。

        // 此外，通道(Channel)一旦被注册，就不能回到阻塞状态。试图这么做的话，
        // 将在调用 configureBlocking( )方法时将抛出IllegalBlockingModeException 异常。

        // 并且，理所当然地，试图注册一个已经关闭的 SelectableChannel 实例的话，
        // 也将抛出ClosedChannelException 异常，就像方法原型指示的那样。

        ssc.configureBlocking(false);
        Selector selector = Selector.open();

        // 注册通道到选择器上，是通过register方法进行的。
        // register()方法接受一个 Selector 对象作为参数，以及一个名为ops 的整数参数。
        // 第二个参数表示所关心的通道操作，
        // 返回值是一个SelectionKey。
        // ops值在 SelectonKey 类中被定义为 public static 字段.

        // 可以看到有四种被定义的可选择操作：读(read)，写(write)，连接(connect)和接受(accept)。
        // 并非所有的操作都在所有的可选择通道上被支持。例如， SocketChannel 不支持 accept。
        // 试图注册不支持的操作将导致IllegalArgumentException。
        // 可以通过调用通道的 validOps( )方法来获取特定的通道所支持的操作集合。
//        public abstract class SelectionKey {
//            public static final int OP_READ = 1 << 0;
//            public static final int OP_WRITE = 1 << 2;
//            public static final int OP_CONNECT = 1 << 3;
//            public static final int OP_ACCEPT = 1 << 4;
//        }

        // SocketChannel支持的validOps ：
//        public abstract class SocketChannel...{
//            public final int validOps() {
//                return (SelectionKey.OP_READ
//                        | SelectionKey.OP_WRITE
//                        | SelectionKey.OP_CONNECT);
//            }
//        }

        //ServerSocketChannel的validOps
//        public abstract class ServerSocketChannel..{
//            public final int validOps() {
//                return SelectionKey.OP_ACCEPT;
//            }
//        }
        SelectionKey sscSelectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);


        // SelectionKey对象被register()方法返回，并提供了方法来表示这种注册关系。
        //public abstract SelectableChannel channel( ); //获得这个SelectionKey关联的channel
        //public abstract Selector selector( ); //获得这个selectionKey关联的Selector
        sscSelectionKey.channel();
        sscSelectionKey.selector();

        // SelectionKey包含指示了该注册关系所关心的通道操作，以及通道已经准备好的操作。
        // 可以通过调用SelectionKey的readyOps()方法来获取相关的通道的已经就绪的操作。
        // ready 集合是 interest集合的子集 。当前的 interest 集合可以通过
        // 调用SelectionKey对象的 interestOps( )方法来获取。
        // 这应该是通道被注册时传进来的值。这个 interset 集合永远不会被选择器改变，
        // 用户可以通过调用带参数的interestOps方法，并传入一个新的比特掩码参数来改变它。
        // public abstract int interestOps();//感兴趣兴趣的操作
        // public abstract int readyOps( );//感兴趣的操作中，已经准备就绪的操作
        // public abstract void interestOps (int ops);// 改变感兴趣的操作
        sscSelectionKey.interestOps();
        int i = sscSelectionKey.readyOps();


        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                continue;
            }
            sc.configureBlocking(false);
            //注册SocketChannel
            SelectionKey scselectionKey = sc.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_WRITE);
            //...其他操作

            // 检查操作是否就绪
            if ((sscSelectionKey.readyOps( ) & SelectionKey.OP_READ) != 0)
            {
                //myBuffer.clear( );
                //sscSelectionKey.channel().read(myBuffer);
                //doSomethingWithBuffer (myBuffer.flip( ));
            }

            // 就像之前提到过的那样，有四个通道操作可以被用于测试就绪状态。
            // 您可以像上面的代码那样，通过测试比特掩码来检查这些状态，
            // 但 SelectionKey 类定义了四个便于使用的布尔方法来为您测试这些比特值：
            // isReadable( )， isWritable( )， isConnectable( )， 和 isAcceptable( )。
            // 每一个方法都与使用特定掩码来测试 readyOps( )方法的结果的效果相同。例如：

            if (sscSelectionKey.isWritable()){};
            // 等价于：
            if ((sscSelectionKey.readyOps( ) & SelectionKey.OP_WRITE) != 0){}
            // 这四个方法在任意一个 SelectionKey 对象上都能安全地调用。

            // 关闭通道和关闭选择器

            // SelectionKey对象表示了一种特定的注册关系(某个Channel注册到某个Selector上)。
            // 当应该终结这种关系的时候，可以调用 SelectionKey对象的 cancel( )方法。
            sscSelectionKey.cancel();
            // 当键被取消时，它将被放在相关的Selector的已取消的键SelectionKey的集合里。
            // 注册不会立即被取消，但键SelectionKey会立即失效。
            // 当再次调用select()方法时（或者一个正在进行的 select()调用结束时），
            // 已取消的键的集合中的被取消的SelectionKey将被清理掉，并且相应的注销也将完成。
            // 通道会被注销，而新的SelectionKey将被返回。

            // 当通道关闭时，所有相关的键会自动取消（记住，一个通道可以被注册到多个选择器上）。
            // 当选择器关闭时，所有被注册到该选择器的通道都将被注销，
            // 并且相关的键将立即被无效化（取消）。一旦键被无效化，
            // 调用它的与选择相关的方法就将抛出 CancelledKeyException。
        }

        // 1. 一个通道可以被注册到多个选择器上，但对每个选择器而言，最好只注册一次。
        // 2. 如果一个Selector上多次注册同一个Channel，返回的SelectionKey总是同一个实例，
        // 后注册的感兴趣的操作类型会覆盖之前的感兴趣的操作类型。
        // 3. 一个channel在不同的selector上注册，每次返回的selectorKey都是一个不同的实例。

    }

    private static void createSelector() throws IOException {
        // 方式一：Selector对象是通过调用静态工厂方法 open()来实例化的.
        // 选择器不是像通道或流(stream)那样的基本I/O对象：
        // 数据从来没有通过它们进行传递。类方法 open()向 SPI 发出请求，
        // 通过默认的 SelectorProvider 对象获取一个新的实例。通过调
        // 用一个自定义的 SelectorProvider对象的 openSelector( )方法来创建一个 Selector 实例也是可行的。
        Selector selector = Selector.open();

        // 方式二：您可以通过调用 provider()方法来决定由哪个SelectorProvider对象来创建给定的 Selector 实例。
        // 大多数情况下，您不需要关心 SPI;只需要调用 open( )方法来创建新的 Selector 对象。
        SelectorProvider provider = SelectorProvider.provider();
        Selector abstractSelector = provider.openSelector();
    }
}
