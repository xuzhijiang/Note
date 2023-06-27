package com.java.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

// java操作mongo
public class MainClass {
    public static void main(String[] args) {
        // 连接mongo服务器
        MongoClient client = new MongoClient("192.168.32.150", 27017);
        // 得到要操作的数据库
        MongoDatabase spitdb = client.getDatabase("spitdb");
        // 得到要操作的集合
        MongoCollection<Document> spitdbCollection = spitdb.getCollection("spit");

        /**
         * 封装查询条件: 查询userid为1013的所有文档
         */
        BasicDBObject bson1= new BasicDBObject("userid", "1013");
        // 得到集合中的所有文档
        FindIterable<Document> documents = spitdbCollection.find(bson1);
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("userid: " + document.getString("userid"));
            System.out.println("访问量: " + document.getInteger("visits"));
            System.out.println("======================================");
        }

        System.out.println();
        System.out.println();

        /**
         * 封装查询条件: 查询访问量大于1000的: find(visits: {$gt: 1000})
         */
        BasicDBObject bson2 = new BasicDBObject("visits", new BasicDBObject("$gt", 1000));
        documents = spitdbCollection.find(bson2);
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("userid: " + document.getString("userid"));
            System.out.println("访问量: " + document.getInteger("visits"));
            System.out.println("======================================");
        }

        /**
         * 添加查询记录
         */
        Map<String, Object> map = new HashMap<>();
        map.put("_id", "7");
        map.put("content", "时间过的好快呀");
        map.put("userid", "1015");
        map.put("visits", 5000);
        Document document = new Document(map);
        spitdbCollection.insertOne(document);

        client.close();
    }
}
