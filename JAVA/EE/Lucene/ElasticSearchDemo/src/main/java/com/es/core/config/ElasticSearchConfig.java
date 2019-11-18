package com.es.core.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticSearchConfig {

    private static final String SCHEMA = "http";

    /** 集群地址,多个地址之间用,隔开 */
    @Value("${spring.data.elasticsearch.host}")
    private String host;
    @Value("${spring.data.elasticsearch.port}")
    private Integer port;
    @Value("${spring.data.elasticsearch.connectTimeOut}")
    private Integer connectTimeOut;
    @Value("${spring.data.elasticsearch.socketTimeOut}")
    private Integer socketTimeOut;
    @Value("${spring.data.elasticsearch.connectionRequestTimeOut}")
    private Integer connectionRequestTimeOut;
    @Value("${spring.data.elasticsearch.maxConnectNum}")
    private Integer maxConnectNum;
    @Value("${spring.data.elasticsearch.maxConnectPerRoute}")
    private Integer maxConnectPerRoute;

    private RestClientBuilder builder;

    private List<HttpHost> httpHostList = new ArrayList<>();

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient client() {
        String[] hosts = host.split(",");
        for (String h : hosts) {
            HttpHost httpHost = new HttpHost(h, port, SCHEMA);
            httpHostList.add(httpHost);
        }

        builder = RestClient.builder(httpHostList.toArray(new HttpHost[0]));
        // 构建RestHighLevelClient对象
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        return restHighLevelClient;
    }
}
