package com.es.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.es.core.service.SearchService;
import com.es.core.util.ESUtils;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.highlight.Highlighter;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    RestHighLevelClient restHighLevelClient;

    @Value("${spring.data.elasticsearch.index.name}")
    private String index;

    @Value("${spring.data.elasticsearch.index.typeName}")
    private String typeName;

    @Override
    public void createIndex() throws Exception{
        // 创建的请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 配置settings
        ESUtils.settingsBuilder(request);
        // 配置映射
        //ESUtils.shopMappingBuilder(request, typeName);
        ESUtils.shopMappingBuilder2(request, typeName);
        // 创建索引
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        // 返回true表示索引创建成功
        // 到kibana查看映射: GET /shop/_mappings
        System.out.println(response + "----" + response.isAcknowledged());
    }

    // es自己维护主键
    @Override
    public void addDocument(String json) throws Exception {
        // 参数1: 索引名  参数2: document类型
        IndexRequest request = new IndexRequest(index, typeName);
        // 处理json,把传入的json转换成XContentType.JSON格式
        request.source(json, XContentType.JSON);
        // 新增数据
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("---->" + response.status());
    }

    // 根据业务数据的id作为主键
    @Override
    public void add(String json, String id) throws Exception {
        // 参数1: 索引名  参数2: document类型 参数3: 业务主键
        IndexRequest request = new IndexRequest(index, typeName, id);
        // 处理json,把传入的json转换成XContentType.JSON格式
        request.source(json, XContentType.JSON);
        // 新增数据
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("---->设置业务主键方式的新增:" + response.status());
    }

    @Override
    public void update(String json, String id) throws Exception {
        // 参数1: 索引名  参数2: document类型 参数3: 业务主键
        IndexRequest request = new IndexRequest(index, typeName, id);
        // 处理json,把传入的json转换成XContentType.JSON格式
        request.source(json, XContentType.JSON);
        // 新增数据
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("---->更新:" + response.status());
    }

    @Override
    public void deleteIndex() throws Exception {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        // 删除索引
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("----->delete index: " + index + ", 结果: " + response.isAcknowledged());
    }

    @Override
    public void deleteDocument(String id) throws Exception {
        DeleteRequest request = new DeleteRequest(index, typeName, id);
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println("----->根据id删除数据: " + response.getResult());
    }

    @Override
    public void searchAll() throws Exception {
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types("shop");// 指定查询的索引库(只查询shop这个库下的)

        // 查询的资源对象
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        builder.query(matchAllQueryBuilder);

        searchRequest.source(builder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println("总记录数: " + hits.getTotalHits());

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            Map<String, DocumentField> map = searchHit.getFields();
//            for (Map.Entry f : map.entrySet()) {
//                System.out.println(f.getKey() + " ---------- " + f.getValue());
//            }
            System.out.println(map);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Override
    public void searchMatch() throws Exception {
        // 1. 构建查询请求对象
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.types("shop");// 指定查询的索引库
        // 2. 构建查询方式,match_all, match, multi_match, _source, bool range sort aggregation
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 匹配查询
        // name: keyword 不分词, 适合聚合, 如果你想要分词,name就要设置为text类型的
        // QueryBuilder queryBuilder = new MatchQueryBuilder("name", "返老还童");
        QueryBuilder queryBuilder = new MatchQueryBuilder("name", "返老还童丸6");
        searchSourceBuilder.query(queryBuilder);

        searchRequest.source(searchSourceBuilder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        // 命中(是否查到结果)
        System.out.println("记录数: " + hits.getTotalHits());
        SearchHit[] result = hits.getHits();
        for (SearchHit documentFields : result) {
            Map<String, Object> map = documentFields.getSourceAsMap();
            System.out.println("结果: " + map);
        }
    }

    // 多字段匹配
    @Override
    public void searchMultiMatch() throws Exception {
        SearchRequest request = new SearchRequest();
        request.types(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        // name中带有三只松鼠和desc中带有三只松鼠的都可以查询出来
        // GET /shop/_search
        //{
        //  "query": {
        //    "multi_match": {
        //      "query": "三只松鼠",
        //      "fields": ["desc","name"]
        //    }
        //  }
        //}
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("三只松鼠", "name", "desc");
        builder.query(queryBuilder);

        request.source(builder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        
        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void termsQuery() throws Exception {
        SearchRequest request = new SearchRequest();
        request.types(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("desc", "好");
        builder.query(queryBuilder);

        request.source(builder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void filterQuery() throws Exception {
        SearchRequest request = new SearchRequest();
        request.types(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("好吃", "name", "desc");
        builder.query(queryBuilder);

        // 过滤结果,把不需要的字段过滤掉
        String[] includeStr = {"name", "price"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includeStr, null);
        builder.fetchSource(fetchSourceContext);

        request.source(builder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void boolQuery() throws Exception {
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // name中必须带有 "三只松鼠" 的("三只松鼠2"也满足)
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "三只松鼠"));
        // name中不能带有 "返老" 的
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "返老"));
        // should是或的关系
        boolQueryBuilder.should(QueryBuilders.matchQuery("sellpoint", "手机"));

        builder.query(boolQueryBuilder);

        request.source(builder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void rangeQuery() throws Exception {
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder builder = new SearchSourceBuilder();
        /*
                GET _search
                {
                  "query": {
                    "range": {
                      "price": {
                        "gte": 10,
                        "lte": 5000
                      }
                    }
                  }
                }
         */
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
        rangeQueryBuilder.gte(10);
        rangeQueryBuilder.lte(9000);
        builder.query(rangeQueryBuilder);
        request.source(builder);

        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void fuzzyQuery() throws Exception {
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder builder = new SearchSourceBuilder();
        /*
                GET /_search
                {
                  "query": {
                    "fuzzy": {
                      "title": "ppo"
                    }
                  }
                }
         */
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "ppo");
        fuzzyQueryBuilder.fuzziness(Fuzziness.AUTO);
        builder.query(fuzzyQueryBuilder);
        request.source(builder);

        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void sortQuery() throws Exception {
        SearchRequest request = new SearchRequest();
        request.types("qf");

        /*
                GET /qf/_search
                {
                  "sort": [
                    {
                      "price": {
                        "order": "desc"
                      }
                    }
                  ]
                }
         */
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.sort("price", SortOrder.ASC);
        request.source(builder);

        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        // 满足条件的记录数
        TotalHits totalHits = hits.getTotalHits();
        System.out.println("总记录数: " + totalHits);
        // 获取命中结果
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchMultiMatch: " + searchHit.getSourceAsMap());
        }
    }

    @Override
    public void aggregationQuery() throws Exception {
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 聚合查询
        // 按照商品类别分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("itemType_group").field("itemType");
        // 求每个类别的平均价格
        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_price").field("price"));
        // 求每个类别的最大价格
        termsAggregationBuilder.subAggregation(AggregationBuilders.max("max_price").field("price"));
        builder.aggregation(termsAggregationBuilder);

        request.source(builder);

        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        Aggregations aggregations = response.getAggregations();
        // 根据分组字段的名称得到数据
        Terms terms = aggregations.get("itemType_group");
        List<? extends Terms.Bucket> list = terms.getBuckets();
        for (Terms.Bucket bucket : list) {
            Object key = bucket.getKey();
            Max max = bucket.getAggregations().get("max_price");
            Avg avg = bucket.getAggregations().get("avg_price");
            System.out.println("max: " + max.getValue());
            System.out.println("avg: " + avg.getValue());
            System.out.println("总的数量: " + bucket.getDocCount());
            System.out.println("key: " + key);
        }
    }

    // GET /qf/_search
    //{
    //  "query": {
    //    "match": {
    //      "title": "oppo"
    //    }
    //  },
    //  "highlight": {
    //    "fields": {
    //      "title": {}
    //    }
    //  }
    //}
    // 如何获取高亮
    //     "hits" : [
    //      {
    //        "_index" : "qf",
    //        "_type" : "tb_items",
    //        "_id" : "5",
    //        "_score" : 0.8025915,
    //        "_source" : {
    //          "title" : "oppo",
    //          "sellpoint" : "",
    //          "images" : "http://www.images.com/005.jpg",
    //          "price" : 5899.0,
    //          "status" : 1
    //        },
    //        "highlight" : {
    //          "title" : [
    //            "<em>oppo</em>"
    //          ]
    //        }
    //      } ]
    @Override
    public void search(String json) throws Exception {
        SearchRequest request = new SearchRequest();
        request.types("qf");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 指定查询条件
        Map<String, Object> map = JSON.parseObject(json);
        Object name = map.get("title");// 查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", name);
        sourceBuilder.query(queryBuilder);

        // 分页,默认返回10条
        sourceBuilder.from(0);// 起始记录
        sourceBuilder.size(20);// 每页显示多少条

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // <em style="color:red;">三</em>
        highlightBuilder.preTags("<em style=\"color:red;\">"); // 指定前缀
        highlightBuilder.postTags("</em>");// 后缀
        highlightBuilder.field("title");// 指定哪一个字段要高亮显示
        highlightBuilder.requireFieldMatch(false);
        sourceBuilder.highlighter(highlightBuilder);

        request.source(sourceBuilder);
        // 执行查询
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("总记录数: " + hits.getTotalHits());
        SearchHit[] result = hits.getHits();
        for (SearchHit documentFields : result) {
            // "highlight" : {
            //          "title" : [
            //            "<em>oppo</em>"
            //          ]
            //        }
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField value = highlightFields.get("title");
            System.out.println("高亮: " + value);
            System.out.println(documentFields.getSourceAsMap());
        }

    }

}
