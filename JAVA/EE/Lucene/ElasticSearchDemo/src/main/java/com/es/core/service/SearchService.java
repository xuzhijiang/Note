package com.es.core.service;

public interface SearchService {
    void createIndex() throws Exception;

    /**
     * es自动生成id
     */
    void addDocument(String json) throws Exception;

    /**
     * 根据业务数据的id作为主键
     */
    void add(String json,String id) throws Exception;

    void update(String json,String id) throws Exception;

    /**
     * 类似于把数据库给删了
     * @throws Exception
     */
    void deleteIndex() throws Exception;

    /**
     * 根据id删除某条数据
     * @param id
     * @throws Exception
     */
    void deleteDocument(String id) throws Exception;

    void searchAll() throws Exception;

    void searchMatch() throws Exception;

    void searchMultiMatch() throws Exception;

    void termsQuery() throws Exception;

    void filterQuery() throws Exception;

    void boolQuery() throws Exception;

    void rangeQuery() throws Exception;

    void fuzzyQuery() throws Exception;

    void sortQuery() throws Exception;

    /**
     * 聚合查询
     */
    void aggregationQuery() throws Exception;

    void search(String json) throws Exception;
}
