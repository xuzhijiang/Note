package com.es.springboot;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESApp {

    @Autowired
    JestClient jestClient;

    /**
     * Update, Index, Delete, Search
     * 1. Action action = new Curd.Builder(携带的数据).其他信息.build();
     * 2. DocumentResult execute = jestClient.execute(action);
     * 3. 获取结果DocumentResult数据即可.
     */

    /**
     * 测试索引(index),索引在这里的意思就是保存的意思
     */
    @Test
    public void index () throws IOException {
        User user = new User();
        user.setEmail("aaa@aaa.com");
        user.setUserName("bbb");

        Index index = new Index.Builder(user)
                .index("user") // 存到哪个索引库下
                .type("info") // 存到哪个type下
                .build();

        DocumentResult execute = jestClient.execute(index);

        System.out.println("执行?" + execute.getId() + "===>" + execute.isSucceeded());

        // 可以通过 GET user/_search 来查询user索引库下的全部来查看
    }

    @Test
    public void query() throws IOException {
        String queryJson = "";

        Search search = new Search.Builder("")
                .addIndex("user") // 从哪个所以库下查询
                .build();

        SearchResult execute = jestClient.execute(search);

        System.out.println("总记录: " + execute.getTotal());
        System.out.println("最大得分: " + execute.getMaxScore());
        System.out.println("查询到的数据:");
        List<SearchResult.Hit<User, Void>> hits = execute.getHits(User.class);
        hits.forEach((hit) -> {
            User user = hit.source;
            System.out.println(user);
        });
    }
}
