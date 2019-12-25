package org.java.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 使用lombok实现链式编程
 */
public class LombokChainDemo {

    public static void main(String[] args) {
        // 链式编程
        Book book = new Book().setId(20).setName("JavaCore").setPrice(50.8d);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    private static class Book {
        private int id;
        private String name;
        private double price;
    }
}
