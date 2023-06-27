package com.tuling.seckill.service;

import com.tuling.seckill.dao.OrderDao;
import com.tuling.seckill.model.Order;
import com.tuling.seckill.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductService productService;

    public Order getOrderById(Long id) {
        return orderDao.getOrderById(id);
    }

    @Transactional
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderDao.deleteOrder(id);
    }

    @Transactional
    public void seckill(Long productId) {
        // 查询商品信息
        Product product = productService.getProductById(productId);

        if (product.getStock() <= 0) {
            throw new RuntimeException("商品已经售罄");
        }

        // 减库存不能这么写,你已经在写bug了,因为并发会导致超卖
        // int stockNum = product.getStock() - 1;

        // 创建订单
        Order order = new Order();
        order.setProductId(productId);
        order.setAmount(product.getPrice());
        saveOrder(order);

        // 减库存
        int updateNum = productService.deductProductStock(productId); // 判断一下更新的那一行库存的sql是否更新成功了,updateNum是更新成功的条数
        if (updateNum <= 0) { // 如果没有更新成功任何的行记录,说明库存可能已经卖完了.
            // 所以要抛异常给前台,说明你没有下单成功.
            throw new RuntimeException("商品已售罄");
        }
    }

    @Transactional
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }

}
