package com.spring.tx.core.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultiService {

    @Autowired
    BookService bookService;

    // 好的解释视频: https://www.bilibili.com/video/BV1uE411C7CW?p=111
    @Transactional
    public void multiTx() {
        // 传播行为就是来设置 这个checkout事务方法是不是和大事务(multiTx方法的事务)共享一个事务的
        // 共享一个事务的意思就是: 使用同一条Connection.
        // 调用bookService中checkout()方法,这个purchase方法也添加了事务@Transactional,
        // checkout到底是用自己的事务呢,还是使用multiTx方法的事务呢.
        // 这个就是事务的传播行为.
        // REQUIRED REQUIRES_NEW
        bookService.checkout(1, "1001");

        // REQUIRED REQUIRED
        bookService.updatePrice("1002", 75.05d);
    }

    /**
     * 问题: bookService.checkout中的 @Transactional(propagation=Propagation.REQUIRED, timeout = 3),
     * 也就是checkout这个事务方法的timeout设置为了3秒,传播行为是REQUIRED.
     * 而外层的大事务multiTx没有设置timeout,请问, checkout的timeout会起作用吗??
     *
     * 答: 不起作用,因为checkout的传播行为是REQUIRED, 所以你用的是人家multiTx的事务设置,所以你自己的事务设置不起作用,
     *
     * 如果checkout的传播行为是REQUIRES_NEW,那么checkout自己的事务属性设置,比如timeout就会起作用.
     */
}
