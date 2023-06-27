package com.spring.tx.core.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("bookShopDao")
public class BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 减去某个用户的余额
     *
     * @param userId 要给哪个用户更新余额
     * @param bookPrice 要更新的余额的数量
     */
    public void updateBalance(int userId, double bookPrice) {
        String sql = "update account set balance = balance - ? where id = ?";
        //int i = 10/0;
        jdbcTemplate.update(sql, bookPrice, userId);
    }

    /**
     * 根据isbn查询图书价格
     *
     * @param isbn 书号
     */
    public double getBookPriceByIsbn(String isbn) {
        String sql = "select price from book where isbn = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, isbn);
    }

    /**
     * 减库存
     *
     * @param isbn 根据书号更新图书的库存,本来是你买几本书就减几次,为了简单起见,这里每次只买一本图书
     */
    public void updateBookStock(String isbn) {
        String sql = "update book_stock set stock = stock - 1 where isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    /**
     * 改书的价格
     * @param isbn
     * @param price
     */
    public void updatePrice(String isbn, double price) {
        String sql = "update book set price=? where isbn=?";
        jdbcTemplate.update(sql, price, isbn);
    }
}
