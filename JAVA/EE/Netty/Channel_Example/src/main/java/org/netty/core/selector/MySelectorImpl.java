package org.netty.core.selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashSet;
import java.util.Set;

/**
 * Selector类的核心是选择过程.Selector是对 select( )、 poll( )等本地调用(native call)或者类似的操作系统特定的系统调用的一个包装.
 *
 * Selector 类的 select( )方法有以下三种不同的形式：
 *
 * public abstract int select( ) throws IOException;
 * public abstract int select (long timeout) throws IOException;
 * public abstract int selectNow( ) throws IOException;
 * 这三种 select 的形式，仅仅在它们在所注册的通道当前都没有就绪时，是否阻塞的方面有所不同。最简单的没有参数的形式可以用如下方式调用：
 *
 *
 * 选择操作是当三种形式的 select()中的任意一种被调用时，由选择器执行的。不管是哪一种形式的调用，下面步骤将被执行：
 *
 *  1、已取消的键的集合将会被检查。如果它是非空的，每个已取消的键的集合中的键将从另外两个集合中移除，
 *  并且相关的通道将被注销。这个步骤结束后，已取消的键的集合将是空的。
 *  2、已注册的键的集合中的键的 interest 集合将被检查。在这个步骤中的检查执行过后，对interest 集合的改动不会影响剩余的检查过程。
 *  一旦就绪条件被定下来，底层操作系统将会进行查询，以确定每个通道所关心的操作的真实就绪状态。
 *  依赖于特定的 select( )方法调用，如果没有通道已经准备好，线程可能会在这时阻塞，通常会有一个超时值。
 *  直到系统调用完成为止，这个过程可能会使得调用线程睡眠一段时间，然后当前每个通道的就绪状态将确定下来。
 *  对于那些还没准备好的通道将不会执行任何的操作。对于那些操作系统指示至少已经准备好 interest 集合中
 *  的一种操作的通道，将执行以下两种操作中的一种：
 *
 *    a.如果通道的键还没有处于已选择的键的集合中，那么键的 ready 集合将被清空，然后表示操作系统发现的当前通道已经准备好的操作的比特掩码将被设置。
 *
 *    b.否则，也就是键在已选择的键的集合中。键的 ready 集合将被表示操作系统发现的当前已经准备好的操作的比特掩码更新。所有之前的已经不再是就绪状态的操作不会被清除。事实上，所有的比特位都不会被清理。由操作系统决定的 ready 集合是与之前的 ready 集合按位分离的，一旦键被放置于选择器的已选择的键的集合中，它的 ready 集合将是累积的。比特位只会被设置，不会被清理。
 *
 *  3.步骤2可能会花费很长时间，特别是所激发的线程处于休眠状态时。与该选择器相关的键可能会同时被取消。
 *  当步骤 2 结束时，步骤 1 将重新执行，以完成任意一个在选择进行的过程中，键已经被取消的通道的注销。
 *
 *  4.select 操作返回的值是 ready 集合在步骤 2 中被修改的键的数量，而不是已选择的键的集合中的通道的总数。
 *  返回值不是已准备好的通道的总数，而是从上一个 select( )调用之后进入就绪状态的通道的数量。之前的调用中就绪的，
 *  并且在本次调用中仍然就绪的通道不会被计入，而那些在前一次调用中已经就绪但已经不再处于就绪状态的通道也不会被计入。
 *  这些通道可能仍然在已选择的键的集合中，但不会被计入返回值中。返回值可能是 0。
 *
 * 使用内部的已取消的键的集合来延迟注销，是一种防止线程在取消键时阻塞，并防止与正在进行的选择操作冲突的优化。
 * 注销通道是一个潜在的代价很高的操作，这可能需要重新分配资源（请记住，键是与通道相关的，并且可能与它们相关的
 * 通道对象之间有复杂的交互）。清理已取消的键，并在选择操作之前和之后立即注销通道，可以消除它们可能正好在选择
 * 的过程中执行的潜在棘手问题。这是另一个兼顾健壮性的折中方案。
 *
 *
 * 停止选择过程
 *
 * Selector 的 API 中的最后一个方法， wakeup()，提供了使线程从被阻塞的select( )方法中优雅地退出的能力：
 * public abstract void wakeup( );
 *
 * 有三种方式可以唤醒在select( )方法中睡眠的线程：
 *
 * A. 调用 wakeup( )
 *
 * 调用 Selector 对象的 wakeup( )方法将使得选择器上的第一个还没有返回的选择操作立即返回。
 * 如果当前没有在进行中的选择，那么下一次对 select( )方法的一种形式的调用将立即返回。
 * 后续的选择操作将正常进行。在选择操作之间多次调用 wakeup( )方法与调用它一次没有什么不同。
 *
 * 有时这种延迟的唤醒行为并不是您想要的。您可能只想唤醒一个睡眠中的线程，而使得后续的选择继续正常地进行。
 * 您可以通过在调用 wakeup( )方法后调用 selectNow( )方法来绕过这个问题。尽管如此，如果您将您的代码构
 * 造为合理地关注于返回值和执行选择集合，那么即使下一个 select( )方法的调用在没有通道就绪时就立即返回，
 * 也应该不会有什么不同。不管怎么说，您应该为可能发生的事件做好准备。
 *
 * B. 调用 close( )
 *
 * 如果Selector的 close( )方法被调用，那么任何一个在选择操作中阻塞的线程都将被唤醒，
 * 就像wakeup( )方法被调用了一样。与选择器相关的通道将被注销， 而键将被取消。
 *
 * C, 调用 interrupt( )
 *
 * 如果睡眠中的线程的 interrupt( )方法被调用，它的返回状态将被设置。如果被唤醒的线程之后将试图在通道上执行 I/O 操作，
 * 通道将立即关闭，然后线程将捕捉到一个异常。这是由于在第三章中已经探讨过的通道的中断语义。使用
 * wakeup( )方法将会优雅地将一个在 select( )方法中睡眠的线程唤醒。如果您想让一个睡眠的线程在直接中断之后继续执行，
 * 需要执行一些步骤来清理中断状态（参见 Thread.interrupted( )的相关文档）。Selector 对象将捕捉 InterruptedException
 * 异常并调用 wakeup( )方法。请注意这些方法中的任意一个都不会关闭任何一个相关的通道。中断一个选择器与中断一个
 * 通道是不一样的。选择器不会改变任意一个相关的通道，它只会检查它们的状态。当一个在 select( )方法中睡眠的线程
 * 中断时，对于通道的状态而言，是不会产生歧义的。
 *
 */
public class MySelectorImpl extends MyAbstractSelector{

    protected Set<SelectionKey> selectedKeys = new HashSet();//选择的key
    protected HashSet<SelectionKey> keys = new HashSet();//注册的keys

    public MySelectorImpl(SelectorProvider provider){
        super(provider);
    }

    @Override
    protected void implCloseSelector() throws IOException {

    }

    @Override
    protected SelectionKey register(AbstractSelectableChannel ch, int ops, Object att) {
        return null;
    }

    /**
     * 就绪选择的第三种也是最后一种形式是完全非阻塞的：
     * int n = selector.selectNow( );
     *
     * selectNow()方法执行就绪检查过程，但不阻塞。如果当前没有通道就绪，它将立即返回 0。
     * @return
     * @throws IOException
     */
    @Override
    public int selectNow() throws IOException {
        return 0;
    }

    /**
     * 有时您会想要限制线程等待通道就绪的时间。这种情况下，可以使用一个接受一个超时参数的select( long timeout)方法的重载形式：
     * 这种调用与之前的例子完全相同，除了如果在您提供的超时时间（以毫秒计算）内没有通道就绪时，它将返回 0。
     * 如果一个或者多个通道在时间限制终止前就绪，键的状态将会被更新，并且方法会在那时立即返回。
     *
     * @param timeout 将超时参数指定为 0 表示将无限期等待，那么它就在各个方面都等同于使用无参数版本的 select( )了。
     * @return
     * @throws IOException
     */
    @Override
    public int select(long timeout) throws IOException {
        return 0;
    }

    /**
     * 这种调用在没有通道就绪时将无限阻塞。一旦至少有一个已注册的通道就绪，选择器的选择键就会被更新，
     * 并且每个就绪的通道(对应于一个SelectionKey)的ready集合也将被更新。
     *
     * @return 返回值将会是已经确定就绪的通道的数目。正常情况下,这些方法将返回一个非零的值，因为直到一个通道就绪前它都会阻塞。
     * 如果选择器的 wakeup( )方法被其他线程调用,它也可以返回非 0 值.
     * @throws IOException
     */
    @Override
    public int select() throws IOException {
        return 0;
    }

    @Override
    public Selector wakeup() {
        return null;
    }
}
