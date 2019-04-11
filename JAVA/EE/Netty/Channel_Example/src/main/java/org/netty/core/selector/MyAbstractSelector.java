package org.netty.core.selector;

import sun.nio.ch.Interruptible;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

// AbstractSelector源码解析
/**
 * Selector维护着注册过的通道的集合，并且这些注册关系中的任意一个都是封装在
 * SelectionKey对象中的。每一个Selector对象维护三个键的集合：
 *
 * 已注册的键的集合(Registered key set)
 * 已选择的键的集合(Selected key set)
 * 已取消的键的集合(Cancelled key set)
 *
 * 在一个刚初始化的 Selector 对象中，这三个集合都是空的。
 *
 *
 */
public abstract class MyAbstractSelector extends Selector {

    private AtomicBoolean selectorOpen = new AtomicBoolean(true);

    // The provider that created this selector
    private final SelectorProvider provider;

    /**
     * Initializes a new instance of this class.
     *
     * @param  provider
     *         The provider that created this selector
     */
    protected MyAbstractSelector(SelectorProvider provider) {
        this.provider = provider;
    }

    /**
     * 已注册的键的集合(Registered key set)
     *
     * 与Selector关联的已经注册的键的集合。并不是所有注册过的键都仍然有效。
     * 这个集合通过keys()方法返回，并且可能是空的。这个已注册的键的集合不是可以直接修改的；
     * 试图这么做的话将引 java.lang.UnsupportedOperationException。
     * @return
     */
    @Override
    public Set<SelectionKey> keys() {
        return null;
    }

    /**
     * 已选择的键的集合(Selected key set)
     *
     * 已注册的SelectionKey的集合的子集。这个集合的每个成员都是相关的通道被选择器
     * （在前一个选择操作中）判断为已经准备好的，
     * 并且包含于键的interest集合中的操作。这个集合通过 selectedKeys( )方法返回（并有可能是空的）。
     *
     * 不要将已选择的键的集合与 ready 集合弄混了。
     * 这是一个SelectionKey的集合，每个SelectionKey都关联一个已经准备好至少一种操作的通道。
     * 每个SelectionKey都有一个内嵌的 ready 集合，指示了所关联的通道已经准备好的操作。
     * (readyOps，一个通道可能对多个操作感兴趣，ready的可能只是其中某个操作)。
     *
     * SelectionKey可以直接从这个集合中移除，但不能添加。
     * 试图向已选择的键的集合中添加元素将抛出java.lang.UnsupportedOperationException。
     * @return
     */
    @Override
    public Set<SelectionKey> selectedKeys() {
        return null;
    }


    // 已取消的键的集合(Cancelled key set)
    /**
     * 已取消的键的集合(Cancelled key set)
     *
     * 已注册的键的集合的子集，这个集合包含了cancel()方法被调用过的键（这个键已经被无效化），
     * 但它们还没有被注销。这个集合是Selector对象的私有成员，因而无法直接访问。
     */
    private final Set<SelectionKey> cancelledKeys = new HashSet<SelectionKey>();

    void cancel(SelectionKey k) {                       // package-private
        synchronized (cancelledKeys) {
            cancelledKeys.add(k);
        }
    }

    /**
     * Closes this selector.
     */
    public final void close() throws IOException {
        boolean open = selectorOpen.getAndSet(false);
        if (!open)
            return;
        implCloseSelector();
    }

    /**
     * Closes this selector.
     */
    protected abstract void implCloseSelector() throws IOException;

    public final boolean isOpen() {
        return selectorOpen.get();
    }

    /**
     * Returns the provider that created this channel.
     */
    public final SelectorProvider provider() {
        return provider;
    }

    /**
     * Retrieves this selector's cancelled-key set.
     *
     * <p> This set should only be used while synchronized upon it.  </p>
     *
     * @return  The cancelled-key set
     */
    protected final Set<SelectionKey> cancelledKeys() {
        return cancelledKeys;
    }

    /**
     * Registers the given channel with this selector.
     *
     */
    protected abstract SelectionKey register(AbstractSelectableChannel ch,
                                             int ops, Object att);


    // -- Interruption machinery --

    private Interruptible interruptor = null;
}
