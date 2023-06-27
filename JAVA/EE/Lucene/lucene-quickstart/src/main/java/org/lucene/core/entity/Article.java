package org.lucene.core.entity;

/**
 * 我们希望: 不论Article的哪一个Field包含用户搜索的关键字，都可以搜索到对应的Article
 *
 * 因此在用户发表Article的时候，我们往数据库中存储记录的时候，同时通过Lucene对Article建立索引
 */
public class Article {
    private Integer id;
    private String title;
    private String content;
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //-----------constructors----------
    public Article(Integer id, String title, String content, String author) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Artical [id=" + id + ", title=" + title + ", content="
                + content + "]";
    }

}