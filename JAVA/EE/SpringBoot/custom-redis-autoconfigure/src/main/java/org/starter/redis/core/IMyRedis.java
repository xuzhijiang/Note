package org.starter.redis.core;

public interface IMyRedis {
    String set(String key, String value);

    String get(String key);
}
