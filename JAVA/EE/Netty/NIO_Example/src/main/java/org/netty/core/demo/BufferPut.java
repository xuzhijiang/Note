package org.netty.core.demo;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 缓冲区存储数据的核心方法：
 * 1. put()：存入数据到缓冲区。
 * 2. get()：获取缓冲区数据。
 */
public class BufferPut {

    public static void main(String[] args) {
        // 将代表“Hello”字符串的 ASCII 码载入一个名为 buffer 的ByteBuffer 对象中
        ByteBuffer buffer = ByteBuffer.allocate(10);
        print(buffer);
        byte H=0x48;
        byte e=0x65;
        byte l=0x6C;
        byte o=0x6F;
        buffer.put(H).put(e).put(l).put(l).put(o);
        // put完成后的缓冲区,见图:5次调用put()之后的缓冲区.png
        print(buffer);
        // 注意本例中我们存储都是字节,当然这样做是比较麻烦的，
        // 也有批量存储的方法，来取代面上一个一个字节的put:
        // buffer.put("hello".getBytes());

        // 我们已经在 buffer 中存放了一些数据，如果我们想在不丢失位置的情况下进行一些更改该怎么办呢？
        // put()的绝对方案可以达到这样的目的。假设想将缓冲区中的内容从“Hello”的 ASCII 码更改为“ Mellow”。我们可以这样实现
        byte M=0x4D;
        byte w=0x77;
        buffer.put(0,M).put(w);
        // 这里通过进行一次绝对方案的 put 将 0 位置的字节代替为十六进制数值 0x4d，将 0x77放入当前位置（当前位置不会受到绝对 put()的影响）的字节，
        // 并将位置属性加一。结果Mellow.png所示.


        // 翻转flip函数:
        // 我们已经往缓冲区中存储了一些数据，现在我们想把它读取出来。但如果通道(Channel)现在在缓冲区上执行 get()，
        // 那么它将从当前position(即6)位置开始读取，也就是我们刚刚插入的有用数据之外的位置，则取出的是未定义数据,见:Mellow.png

        // 如果我们将position值重新设为 0，就可以从正确位置开始获取，但是它是怎样知道何时到达我们所插入数据末端的呢？
        // 这就是limit上界属性被引入的目的。上界属性指明了缓冲区有效内容的末端。我们需要将上界属性设置为当前位置，
        // 然后将位置重置为 0。我们可以人工用下面的代码实现：
        // buffer.limit(buffer.position()).position(0);

        // 但这种从填充到释放状态的缓冲区翻转是 API 设计者预先设计好的，他们为我们提供了一个非常便利的函数：
        // Buffer.flip();
        // flip()函数将一个能够继续添加数据元素的填充状态的缓冲区翻转成一个准备读出元素的释放状态。
        // 在翻转之后，缓冲区的逻辑试图变成如图:flip-mellow.png


        // rewind()函数与 flip()相似，但不影响上界属性。它只是将位置position值设回 0。
        // 您可以使用rewind()后退，重读已经被翻转的缓冲区中的数据。

        // 如果将缓冲区翻转两次会怎样呢？它实际上会大小变为 0。按照flip-mellow.png的相同步骤对缓冲区进行操作；
        // 把上界设为位置的值，并把位置设为 0。上界和位置都变成 0。
        // 尝试对缓冲区上位置和上界都为 0 的 get()操作会导致 BufferUnderflowException 异常。而 put()则会导致 BufferOverflowException 异常。



        // 释放缓冲区(把缓冲区中的数据读到数组中):

        // 现在我们读取数据时，从position位置开始直到limit结束就可以了，
        // 布尔函数 hasRemaining()会在释放缓冲区时告诉您是否已经达到缓冲区的上界。
        // 以下是一种将数据元素从缓冲区释放到一个数组的方法:
        // for (int i = 0; buffer.hasRemaining(); i++) {
        //    myByteArray [i] = buffer.get( );
        // }
        // 作为选择， remaining()函数将告知您从当前位置到上界还剩余的元素数目。 您也可以通过下面的循环来释放缓冲区。
        // int count = buffer.remaining( );
        // for (int i = 0; i < count; i++) {
        //    myByteArray [i] = buffer.get( );
        // }
        //如果您对缓冲区有专门的控制，后一种方法会更高效，因为上界不会在每次循环重复时都被检查。

        // 类似的put，get也有对应的批量操作，我们可以通过以下方式直接读取出，当前buffer中的所有元素
        // buffer.flip();
        // int count = buffer.remaining( );
        // byte[] content=new  byte[count];//构造一个与剩余可读元素大小相同的数组
        // buffer.get(content);
        // System.out.println(new String(content));
        // 当读取完成之后，缓冲区试图如下所示：读取完成后-缓冲区的示意图.png




        // 清空clear函数：
        // 当我们读取完了缓冲区的数据，为了重复利用缓冲区，我们可以通过clear函数来让缓冲区恢复到初始状态，
        // 它并不改变缓冲区中的任何数据元素，而是仅仅将上界设为容量的值，并把位置设回 0，即position=0，limit=capacity，mark=-1。
        // 以下是Buffer类的clear方法的源码:
        /*public final Buffer clear() {
            position = 0;
            limit = capacity;
            mark = -1;
            return this;
        }*/

        // 清空后的缓冲区如图: 清空后的缓冲区.png


        // 标记mark函数与reset函数:
        // 我们已经涉及了缓冲区四种属性中的三种。第四种，mark，使缓冲区能够记住一个position并在之后将其返回。
        // 缓冲区的标记在 mark()函数被调用之前是未定义的，值为-1，调用时mark被设为当前position的值。
        // reset( )函数将position设为当前的mark值。如果mark值未定义，调用 reset( )将导致 InvalidMarkException 异常。
        // 一些缓冲区函数rewind()、clear()、以及 flip()会抛弃已经设定的标记。

        //对于'Mellow'放入ByteBuffer之后，并且执行了flip函数之后，如果执行以下代码片段
        // buffer.position(2).mark().position(4);
        // 那么缓冲区逻辑试图如:mark01.png

        // 如果现在从这个缓冲区读取数据，两个字节（“ow”）将会被发送，而position会前进到 6。
        // 如果我们此时调用reset()，position将会被设为mark，如图mark02.png所示。
        // 再次将读取缓冲区的值将导致四个字节（“llow”）被发送。
        // mark可能没有什么实际意义，但你了解了概念



        // 压缩(compact)
        // 有时，您可能只想从缓冲区中释放一部分数据，而不是全部，然后重新填充。
        // 为了实现这一点，未读的数据元素需要下移以使第一个元素索引为 0。
        // 尽管重复这样做会效率低下，但这有时非常必要，而 API 对此为您提供了一个 compact()函数。

        // 图compact01.png显示了一个已经读取了前2个元素，并且现在我们想要对其进行压缩的缓冲区,调用
        // buffer.compact();
        // 会导致缓冲区如图compact02.png所示
        // 会看到数据元素 2-5 被复制到 0-3 位置。位置 4 和 5 不受影响，
        // 但现在正在或已经超出了当前position，因此是“死的”。它们可以被之后的 put()调用重写。
        // 还要注意的是，position已经被设为被复制的数据元素的数目(4)。
        // 也就是说，缓冲区现在被定位在缓冲区中最后一个“存活”元素后插入数据的位置。
        // 最后，limit属性被设置为capacity的值，因此缓冲区可以被再次填满。
        // 调用 compact()的作用是丢弃已经释放的数据，保留未释放的数据，并使缓冲区对重新填充容量准备就绪。

        // 压缩对于使缓冲区与您从端口中读入的数据（包）逻辑块流的同步来说也许是一种便利的方法
        // (处理粘包、解包的问题)。

        // 复制缓冲区duplicate()函数
        // 缓冲区的复制有分两种：
        // 1、完全复制：调用duplicate()函数或者asReadOnlyBuffer()函数
        // 2、部分复制：调用slice函数

        // duplicate()函数创建了一个与原始缓冲区相似的新缓冲区。两个缓冲区共享数据元素，
        // 拥有同样的容量，但每个缓冲区拥有各自的位置，上界和标记属性。对一个缓冲区内的
        // 数据元素所做的改变会反映在另外一个缓冲区上。这一副本缓冲区具有与原始缓冲区同样的数据视图。
        // 如果原始的缓冲区为只读，或者为直接缓冲区，新的缓冲区将继承这些属性。
//        CharBuffer buffer = CharBuffer.allocate (8);
//        buffer.position (3).limit (6).mark( ).position (5);
//        CharBuffer dupeBuffer = buffer.duplicate( );
//        buffer.clear( );
        // 此时缓冲区的逻辑试图如图duplicate.png:

        // 可以使用asReadOnlyBuffer()函数来生成一个只读的缓冲区视图 。 这与duplicate()相同，
        // 除了这个新的缓冲区不允许使用put()，并且其 isReadOnly()函数将 会 返 回 true 。
        // 对这一只读缓冲区put()函数的调用尝试会导致抛出ReadOnlyBufferException 异常。
    }

    private static void print(Buffer... buffers) {
        for (Buffer buffer : buffers) {
            System.out.println("capacity="+buffer.capacity()
                    +",limit="+buffer.limit()
                    +",position="+buffer.position()
                    +",hasRemaining:"+buffer.hasArray()
                    +",remaining="+buffer.remaining()
                    +",hasArray="+buffer.hasArray()
                    +",isReadOnly="+buffer.isReadOnly()
                    +",arrayOffset="+buffer.arrayOffset());
        }
    }

}
