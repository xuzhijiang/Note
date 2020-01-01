package com.tuling.seckill.zookeeper;

import com.tuling.seckill.common.Constants;
import com.tuling.seckill.controller.SeckillController;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * zk更新缓存watcher
 */
@Component
public class ZookeeperWatcher implements Watcher, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperWatcher.class);

    private static ApplicationContext applicationContext;

    private ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.None && event.getPath() == null) {
            logger.info("=========zookeeper连接成功==========");
            if (zooKeeper == null) {
                zooKeeper = applicationContext.getBean(ZooKeeper.class);
            }
            // 创建zk的商品售完标记根节点
            try {
                // 如果节点不存在,则创建
                if (zooKeeper.exists(Constants.ZK_PRODUCT_SOLD_OUT_FLAG, false) == null) {
                    zooKeeper.create(Constants.ZK_PRODUCT_SOLD_OUT_FLAG, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getType() == Event.EventType.NodeDataChanged) { // zk目录节点数据变化通知事件
            try {
                String path = event.getPath();
                String soldOutFlag = new String(zooKeeper.getData(path, true, new Stat()));
                logger.info("zookeeper数据节点修改变动,path={},value={}", path, soldOutFlag);
                if ("false".equals(soldOutFlag)) { // 表示商品还没有售完,所以要清空jvm缓存.
                    String productId = path.substring(path.lastIndexOf("/") + 1, path.length());
                    SeckillController.getProductSoldOutMap().remove(productId);
                }
            } catch (Exception e) {
                logger.error("zookeeper数据节点修改回调事件异常", e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ZookeeperWatcher.applicationContext = applicationContext;
    }
}
