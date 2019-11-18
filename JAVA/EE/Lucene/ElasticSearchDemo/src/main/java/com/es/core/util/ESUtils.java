package com.es.core.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

public class ESUtils {

    /**
     * {
     *   "settings": {
     *     "number_of_shards": 1,
     *     "number_of_replicas": 0
     *   }
     * }
     */
    public static void settingsBuilder(CreateIndexRequest request) {
        // 创建2分片,数据存储在2个分片上
        request.settings(Settings.builder().put("index.number_of_shards", "2"));
        // 每个分片有3个副本
        request.settings(Settings.builder().put("index.number_of_replicas", "3"));
    }

    /**
     * {
     *   "shop" : {
     *     "mappings" : {
     *       "properties" : {
     *         "desc" : {
     *           "type" : "keyword"
     *         },
     *         "images" : {
     *           "type" : "text",
     *           "index" : false
     *         },
     *         "name" : {
     *           "type" : "keyword"
     *         },
     *         "price" : {
     *           "type" : "float"
     *         }
     *       }
     *     }
     *   }
     * }
     */
    public static void shopMappingBuilder(CreateIndexRequest request, String typeName) {
        try {
            // 相当于构建json字符串
            // startArray():[       startObject():(
            XContentBuilder xContentBuilder = JsonXContent.contentBuilder();
            xContentBuilder.startObject()

                    .startObject("properties")

                    .startObject("name")
                    // text: 分词  keyword:不分词, 不分词的意思是,假如name为"oppo手机", 那么通过oppo就不能把"oppo手机"查出来
                    // 把name这field设置为text后,就可以通过"oppo"把"oppo手机"查出来,这个很关键
                    .field("type", "keyword")
                    .field("index", "true")
                    .endObject()

                    .startObject("desc")
                    .field("type", "keyword")
                    .field("index", "true")
                    .endObject()

                    .startObject("price")
                    .field("type", "float")
                    .field("index", "true")
                    .endObject()

                    .startObject("images")
                    .field("type", "text")
                    .field("index", "false")
                    .endObject()

                    .endObject() // properties end
                    .endObject();

            // 创建映射mapping
            request.mapping(typeName, xContentBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("拼接json错误");
        }
    }

    /**
     * {
     *   "shop" : {
     *     "mappings" : {
     *       "properties" : {
     *         "desc" : {
     *           "type" : "keyword"
     *         },
     *         "images" : {
     *           "type" : "text",
     *           "index" : false
     *         },
     *         "name" : {
     *           "type" : "keyword"
     *         },
     *         "price" : {
     *           "type" : "float"
     *         }
     *       }
     *     }
     *   }
     * }
     */
    public static void shopMappingBuilder2(CreateIndexRequest request, String typeName) {
        try {
            // 相当于构建json字符串
            // startArray():[       startObject():(
            XContentBuilder xContentBuilder = JsonXContent.contentBuilder();
            xContentBuilder.startObject()

                    .startObject("properties")

                    .startObject("name")
                    // text: 分词  keyword:不分词, 不分词的意思是,假如name为"oppo手机", 那么通过oppo就不能把"oppo手机"查出来
                    // 把name这field设置为text后,就可以通过"oppo"把"oppo手机"查出来,这个很关键
                    .field("type", "text")
                    .field("index", "true")
                    .endObject()

                    .startObject("desc")
                    .field("type", "text")
                    .field("index", "true")
                    .endObject()

                    .startObject("price")
                    .field("type", "float")
                    .field("index", "true")
                    .endObject()

                    .startObject("itemType")
                    .field("type", "keyword")
                    .field("index", "false")
                    .endObject()

                    .endObject() // properties end
                    .endObject();

            // 创建映射mapping
            request.mapping(typeName, xContentBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("拼接json错误");
        }
    }

}
