package org.zookeeper.core;

public interface Constant {
    String ZK_CONNECTION_STRING = "your zookeeper host and port";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider" ;
}