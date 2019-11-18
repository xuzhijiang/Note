package com.es.core;

import com.alibaba.fastjson.JSON;
import com.es.core.service.SearchService;
import io.micrometer.core.instrument.search.Search;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainESApp.class)
public class TestES {

    @Resource
    private RestHighLevelClient client;

    @Autowired
    private SearchService searchService;

    @Test
    public void testRestHighLevelClient() {
        System.out.println(client);
    }

    @Test
    public void testCreateIndex() throws Exception {
        searchService.createIndex();
    }

    // es自己维护主键
    @Test
    public void testAddDocument() throws Exception {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "返老还童丸" + i);
            map.put("desc", "服了这粒返老还童丸" + "马上变隔壁小黄");
            map.put("price", 999);

            String json = JSON.toJSONString(map);
            searchService.addDocument(json);
        }
    }

    // 根据业务数据的id作为主键
    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "返老还童丸" + i);
            map.put("desc", "服了这粒返老还童丸" + i + "马上变隔壁小黄");
            map.put("price", 999);

            String json = JSON.toJSONString(map);
            searchService.add(json, String.valueOf(i));
        }
    }

    @Test
    public void testAdd2() throws Exception {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "返老还童丸" + i);
            map.put("desc", "服了这粒返老还童丸" + i + "马上变隔壁小黄");
            map.put("itemType", "保健");
            map.put("price", 999);

            String json = JSON.toJSONString(map);
            searchService.add(json, String.valueOf(i+1));
        }

        for (int i = 10; i < 20; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "三只松鼠" + i);
            map.put("desc", "好吃");
            map.put("itemType", "饮食");
            map.put("price", 19+i);

            String json = JSON.toJSONString(map);
            searchService.add(json, String.valueOf(i+1));
        }
    }

    @Test
    public void testDelete() throws Exception {
        searchService.deleteIndex();
    }

    @Test
    public void testDeleteDocument() throws Exception {
        searchService.deleteDocument(String.valueOf(1));
    }

    @Test
    public void testSearchAll() throws Exception {
        searchService.searchAll();
    }

    @Test
    public void testUpdate() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "勒布朗" + 6);
        map.put("desc", "枸杞哥和唐斯");
        map.put("price", 966);

        String json = JSON.toJSONString(map);
        searchService.update(json, String.valueOf(6));
    }

    @Test
    public void testSearchMatch() throws Exception {
        searchService.searchMatch();
    }

    @Test
    public void testSearchMultiMatch() throws Exception {
        searchService.searchMultiMatch();
    }

    @Test
    public void termsQuery() throws Exception {
        searchService.termsQuery();
    }

    @Test
    public void filterQuery() throws Exception {
        searchService.filterQuery();
    }

    @Test
    public void boolQuery() throws Exception {
        searchService.boolQuery();
    }

    @Test
    public void rangeQuery() throws Exception {
        searchService.rangeQuery();
    }

    @Test
    public void fuzzyQuery() throws Exception {
        searchService.fuzzyQuery();
    }

    @Test
    public void sortQuery() throws Exception {
        searchService.sortQuery();
    }

    @Test
    public void aggregationQuery() throws Exception {
        searchService.aggregationQuery();
    }

    @Test
    public void search() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "oppo");
        searchService.search(JSON.toJSONString(map));
    }
}
