package org.distributed.core.lock.zookeeperlock;

public class Lock {
    private String lockId; // 例如: 888
    private String path; // node的路径: /lock/888.R00001
    private boolean active; // 锁是激活状态还是在等待状态

    public Lock(String lockId, String path) {
        this.lockId = lockId;
        this.path = path;
    }

    public Lock() {}

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
