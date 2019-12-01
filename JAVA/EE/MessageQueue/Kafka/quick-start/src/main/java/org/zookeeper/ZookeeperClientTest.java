package org.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class ZookeeperClientTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zkClient = new ZooKeeper("ip:2181", 3000, (watchedEvent -> {
        }));

        // 持久节点
        //zkClient.create("/node-persistent", "node data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //临时节点
        //zkClient.create("/node_temp", "node temp data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 持久顺序节点
        //zkClient.create("/node-persistent-seq", "node-persistent-seq data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        //zkClient.create("/node-persistent-seq", "node-persistent-seq data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        // 临时顺序节点
        //String path = zkClient.create("/node-temp-seq", "node-temp-seq data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //zkClient.create("/node-temp-seq", "node-temp-seq data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //System.out.println("path: " + path);

        // 监听/aaaaa这个节点是否存在,并且设置了监听事件,当这个节点被创建的时候调用
        zkClient.exists("/aaaaa", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.EventType eventType = watchedEvent.getType();
                System.out.println("eventType: " + eventType.name());
                // 节点的4种事件类型
                // None(-1),
                // NodeCreated(1),
                // NodeDeleted(2),
                // NodeDataChanged(3),
                // NodeChildrenChanged(4);
                if (Event.EventType.NodeDeleted.equals(eventType)) {
                    System.out.println("节点被删除了....");
                }
            }
        });

        List<String> childrens = zkClient.getChildren("/", false);
        System.out.println("/的子节点" + childrens);

        System.in.read();

    }
}
