package org.java.core.base.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JacksonTester {

    /**
     * 对象的序列化与反序列化(jackson操作对象)
     */
    @Test
    public void optObject() throws IOException {
        String jsonStr = "{\"name\":\"xzj\", \"age\":26}";

        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(person);

        String str = objectMapper.writeValueAsString(person);
        System.out.println(str);
    }

    private static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 集合的序列化与反序列化(jackson操作集合)
     */
    @Test
    public void optCollection() throws Exception {
        String jsonString = "{\"draw\":1,\"recordsTotal\":1,\"recordsFiltered\":1,\"data\":[{\"id\":33,\"title\":\"ad1\",\"subTitle\":\"ad1\",\"titleDesc\":\"ad1\",\"url\":\"https://sale.jd.com/act/XkCzhoisOMSW.html\",\"pic\":\"https://m.360buyimg.com/babel/jfs/t20164/187/1771326168/92964/b42fade7/5b359ab2N93be3a65.jpg\",\"pic2\":\"\",\"content\":\"<p><br></p>\"}],\"error\":null}";
        ObjectMapper objectMapper = new ObjectMapper();

        // 反序列化 JSON 到树
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        // 从树中读取 data 节点
        JsonNode jsonData = jsonNode.findPath("data");
        System.out.println("jsonData: " + jsonData.toString());

        // 从树中读取 data 节点
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, TbContent.class);
        List<TbContent> tbContents = objectMapper.readValue(jsonData.toString(), javaType);
        for (TbContent tbContent : tbContents) {
            System.out.println(tbContents);
        }

        String json = objectMapper.writeValueAsString(tbContents);
        System.out.println(json);
    }

    private static class TbContent {
        private Long id;
        private String title;
        private String subTitle;
        private String titleDesc;
        private String url;
        private String pic;
        private String pic2;
        private String content;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getTitleDesc() {
            return titleDesc;
        }

        public void setTitleDesc(String titleDesc) {
            this.titleDesc = titleDesc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "TbContent{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", titleDesc='" + titleDesc + '\'' +
                    ", url='" + url + '\'' +
                    ", pic='" + pic + '\'' +
                    ", pic2='" + pic2 + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
