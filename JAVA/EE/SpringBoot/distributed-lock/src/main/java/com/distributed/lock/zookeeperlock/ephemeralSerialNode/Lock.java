package com.distributed.lock.zookeeperlock.ephemeralSerialNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用临时序号节点实现分布式锁的功能
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lock {
    private String lockId; // 例如: 888
    private String path; // node的路径: /lock/888.R00001
    private boolean active; // 锁是激活状态还是在等待状态

    public Lock(String lockId, String path) {
        this.lockId = lockId;
        this.path = path;
    }
}
