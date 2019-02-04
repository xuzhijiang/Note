package com.jinxuliang.rest_demo.domain;

// 将多个查询参数合成为一个参数对象-1
// http://localhost:8080/myservice/querycondition?usename=John&isMale=true&age=30
// 关键点：查询参数的key，与Java 类中的字段名称一致。
public class UserQueryCondition {
    private String username;
    private int age;
    private boolean isMale;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
