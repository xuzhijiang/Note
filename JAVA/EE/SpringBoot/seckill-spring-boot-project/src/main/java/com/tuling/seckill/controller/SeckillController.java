package com.tuling.seckill.controller;

import com.tuling.seckill.common.Constants;
import com.tuling.seckill.common.ReturnMessage;
import com.tuling.seckill.model.Product;
import com.tuling.seckill.service.OrderService;
import com.tuling.seckill.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class SeckillController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ZooKeeper zooKeeper;

    // Long: 商品的id  Boolean: 商品是否已经售完的标记,默认值为null
    private static ConcurrentHashMap<Long, Boolean> productSoldOutMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Long, Boolean> getProductSoldOutMap() {return productSoldOutMap; }

    @PostConstruct
    public void init() {
        List<Product> products = productService.listProducts();
        // 把所有需要秒杀的商品的库存存到redis中, key为: product_stock_商品id value: 这件商品的库存
        for (Product p : products) {
            // 用最主流的技术,现在很少人使用jedis直接操作了.
            stringRedisTemplate.opsForValue().set(Constants.REDIS_PRODUCT_STOCK_PREFIX + p.getId(), String.valueOf(p.getStock()));
        }
    }

    @GetMapping("/seckill/{productId}")
    public ReturnMessage seckill(@PathVariable("productId") Long productId)
            throws KeeperException, InterruptedException {
        // redis虽然快,但是redis毕竟是一个远程的服务,就算再快,jvm这样一个外部应用程序和redis打交道,仍然有网络开销
        // 如果能够减少这些网络开销,对系统并发的性能提升也是很大的.
        if (productSoldOutMap.get(productId) != null) { // 这个其实就是jvm级别的缓存
            return ReturnMessage.error("商品已售完");
        }

        // redis的原子减操作,多个线程来做减操作,redis后台会帮你排队,不会出现并发问题.这个方法是线程安全的.
        // 返回值是剩余库存
        Long stock = stringRedisTemplate.opsForValue().decrement(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
        if (stock < 0) {
            // 设置这个 productId 的商品已经被售罄!
            productSoldOutMap.put(productId, true);
            log.info("=========设置商品{}售完标记==>>>>", productId);

            // 还原redis中库存,防止少卖,如果不还原,这个stock减到负几十也是有可能的
            stock = stringRedisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
            // 还原之后,最后stock库存是可以还原到0的
            log.info("===========还原stock到=====>>>>> " + stock);

            // 格式: /product_sold_out_flag/商品id
            String zkSoldOutProductPath = Constants.getZKSoldOutProductPath(productId);
            // 判断路径是否存在,如果路径不存在则创建/product_sold_out_flag/productId
            if (zooKeeper.exists(zkSoldOutProductPath, true) == null) {
                // 创建路径的同时 设置值为 true, 而且这个节点是持久节点
                zooKeeper.create(zkSoldOutProductPath, "true".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            // 监听商品售完标记 (也就是对zkSoldOutProductPath这个路径设置监听)
            zooKeeper.exists(zkSoldOutProductPath, true); // 对某个节点 设置监听

            return ReturnMessage.error("商品已售罄");
        }

        try {
            orderService.seckill(productId);
        }catch (Exception e) {
            // 如果下单抛异常了,要还原库存,否则会造成数据库和mysql缓存不一致的问题.这个方法也是原子操作,线程安全的.
            stringRedisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + productId);
            if (productSoldOutMap.get(productId) != null) {
                productSoldOutMap.remove(productId);
            }

            // 设置zk的商品售完标记为false,表示商品还没有售完,把标记设置为false表示,要通知其他分布式jvm,清空他们的jvm缓存.
            if (zooKeeper.exists(Constants.getZKSoldOutProductPath(productId), true) != null) {
                zooKeeper.setData(Constants.getZKSoldOutProductPath(productId), "false".getBytes(), -1);
            }

            log.error("创建订单失败", e);
            return ReturnMessage.error("创建订单失败: " + e.getMessage());
        }
        return ReturnMessage.success();
    }
}
