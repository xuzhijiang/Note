package com.springboot.autoconfiguration.core.config;

public class RedisCfgProperties {

    private String host;

    private Integer port;

    private Integer maxTotal;

    private Integer maxIdle;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    @Override
    public String toString() {
        return "RedisCfgProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", maxTotal=" + maxTotal +
                ", maxIdle=" + maxIdle +
                '}';
    }
}
