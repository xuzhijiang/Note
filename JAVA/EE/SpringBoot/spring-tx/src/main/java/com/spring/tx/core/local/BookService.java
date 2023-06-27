package com.spring.tx.core.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    /**
     * 结账,根据哪个用户买了哪本书来结账.
     *
     * @param userId 哪个用户
     * @param isbn 买了哪本书
     */
    @Transactional(propagation=Propagation.REQUIRED, timeout = 2)
    public void checkout(int userId, String isbn) {
        //1.获取要买的图书的价格
        double bookPrice = bookDao.getBookPriceByIsbn(isbn);
        //2.减库存
        bookDao.updateBookStock(isbn);
        //3.更新用户的余额(账户余额在数据库中是unsigned,不能为负数,所以一旦钱不够买书,事务就会回滚.)
        bookDao.updateBalance(userId, bookPrice);

        // try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace();}
    }

    /**
     * 改书的价格
     * @param isbn
     * @param price
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePrice(String isbn, double price) {
        bookDao.updatePrice(isbn, price);
        int i = 10/0;
    }


    /**
     * 这样不可以测试事务的传播行为.必须要在MultiService中调用才可以,原因:
     *
     * 在MultiService中调用:
     *      MultiService.Proxy.multiTx() {
     *          // 因为调用的BookServiceProxy代理对象的事务方法,所以下面2个调用分别可以进行自己的aop事务控制
     *          BookServiceProxy.checkout();
     *          BookServiceProxy.updatePrice()
     *      }
     *
     * 在BookService中直接调用自己的checkout和updatePrice的效果:
     *
     *      // 在代理对象内部直接调用内部事务方法,每个单独的方法 不能进行单独的aop事务控制.
     *      // 所以下面的效果就是: multiTx,checkout,updatePrice使用的是同一个事务,即使你把checkout,updatePrice
     *      // 的传播行为设置为了REQUIRES_NEW,也就是会把他们当作一个整体的事务.
     *      BookServiceProxy.multiTx() {
     *         checkout();
     *         updatePrice();
     *      }
     */
    @Transactional
    public void multiTx() {
        // REQUIRES_NEW
        checkout(1, "1001");

        // REQUIRES_NEW
        updatePrice("1002", 75.05d);

        int i = 10/0;
    }
}