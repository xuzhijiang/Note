package org.starter.redis.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @ConfigurationPropertie结合相关xxxProperties类来绑定相关的配置
@ConfigurationProperties(prefix = "spring.my.redis")
public class MyRedisProperties {

    private String host;

    private String password;

    private Integer port;

    private Integer maxTotal = 50;

    private Integer minIdle = 5;

    private Integer maxIdle = 20;

    private Integer timeOut = 2000;

    /**从连接池中借出去的jedis都会经过测试*/
    private boolean testOnBorrow = true;
    /**返回jedis池中的jedis实例都会经过测试*/
    private boolean testOnReturn = false;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @Override
    public String toString() {
        return "MyRedisProperties{" +
                "host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", maxTotal=" + maxTotal +
                ", minIdle=" + minIdle +
                ", maxIdle=" + maxIdle +
                ", timeOut=" + timeOut +
                ", testOnBorrow=" + testOnBorrow +
                ", testOnReturn=" + testOnReturn +
                '}';
    }
}
